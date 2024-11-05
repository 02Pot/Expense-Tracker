import ExpenseService from '@/services/ExpenseService';
import IncomeService from '@/services/IncomeService';
import { useCallback, useEffect, useState } from 'react';
import { Helmet, HelmetProvider } from 'react-helmet-async';
import bgImage from '../../assets/bg.jpg';
import './Dashboard.css';
import ExpenseHistory from "./expensehistory/ExpenseHistory";
import Header from "./header/Header";
import CategoryBox from "./incomeexpense/CategoryBox";
import Overview from "./overview/Overview";
import { TransactionExpense, TransactionIncome } from './transactiondialog/Transaction';
import TransactionHistory from "./transactionhistory/TransactionHistory";

function Dashboard(){
    const [incomeEntries, setIncomeEntries] = useState([]);
    const [totalIncome, setTotalIncome] = useState(0);
    const [expenseEntries, setExpenseEntries] = useState([]);
    const [totalExpense, setTotalExpense] = useState(0);
    const [transactions, setTransactions] = useState([]);
    const [activeBox, setActiveBox] = useState(null);
    const [monthlyData, setMonthlyData] = useState({
        labels: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
        income: Array(12).fill(0),
        expense: Array(12).fill(0),
    });

    const addExpenseEntry = useCallback((newExpense) => {
        const date = new Date(newExpense.expenseIncurred);
        const formatDate = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;

        setExpenseEntries((prevEntries) => [...prevEntries, newExpense]);
        setTransactions((prevTransactions) => [
            ...prevTransactions,
            {   id: newExpense.expensesId,
                type: 'Expense',
                amount: newExpense.expensesAmount,
                category: newExpense.expensesCategory,
                description: newExpense.expensesDescription,
                date: formatDate,
            }
        ]);
        setTotalExpense((prevTotal) => prevTotal + Number(newExpense.expensesAmount));
    }, []);

    const addIncomeEntry = useCallback((newIncome) => {
        const date = new Date(newIncome.createdOn);
        const formatDate = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;

        setIncomeEntries((prevEntries) => [...prevEntries, newIncome]);
        setTransactions((prevTransactions) => [
            ...prevTransactions,
            {   id: newIncome.incomeId,
                type: 'Income',
                amount: newIncome.incomeAmount,
                category: newIncome.incomeCategory,
                description: newIncome.incomeDescription,
                date: formatDate,
            }
        ]);
        setTotalIncome((prevTotal) => prevTotal + Number(newIncome.incomeAmount));
    }, []);



    useEffect(() => {
        const calculateMonthlyData = () => {
            const incomeByMonth = Array(12).fill(0);
            const expenseByMonth = Array(12).fill(0);

            incomeEntries.forEach(entry => {
                const month = new Date(entry.createdOn).getMonth();
                incomeByMonth[month] += entry.incomeAmount;
            });

            expenseEntries.forEach(entry => {
                const month = new Date(entry.expenseIncurred).getMonth();
                expenseByMonth[month] += entry.expensesAmount;
            });

            setMonthlyData({
                labels: monthlyData.labels,
                income: incomeByMonth,
                expense: expenseByMonth,
            });
        };

        calculateMonthlyData();
    }, [incomeEntries, expenseEntries]);

    useEffect(() => {
        const fetchAllData = async () => {
            try {
                const incomeResponse = await IncomeService.getAllIncomes();
                const expenseResponse = await ExpenseService.getAllExpenses();
                
                setIncomeEntries(incomeResponse);
                setExpenseEntries(expenseResponse);

                const totalInc = incomeResponse.reduce((acc, income) => acc + income.incomeAmount, 0);
                const totalExp = expenseResponse.reduce((acc, expense) => acc + expense.expensesAmount, 0);

                setTotalIncome(totalInc);
                setTotalExpense(totalExp);

                const expenseMapped = expenseResponse.map(expense => ({
                    category: expense.expensesCategory,
                    description: expense.expensesDescription,
                    date: new Date(expense.expenseIncurred).toISOString().split('T')[0],
                    type: "Expense",
                    amount: expense.expensesAmount,
                    id: expense.expensesId
                }));

                const incomeMapped = incomeResponse.map(income => ({
                    category: income.incomeCategory,
                    description: income.incomeDescription,
                    date: new Date(income.createdOn).toISOString().split('T')[0],
                    type: "Income",
                    amount: income.incomeAmount,
                    id: income.incomeId
                }));

                setTransactions([...expenseMapped, ...incomeMapped]);
            } catch (error) {
                console.error("Error data:", error);
            }
        };
        fetchAllData();
    }, []);

    const handleDelete = async (id, type) => {
        try {
            if (type === "Expense") {
                await ExpenseService.deleteExpense(id);
                setExpenseEntries(prev => prev.filter(expense => expense.expensesId !== id));
                setTransactions(prev => prev.filter(transaction => transaction.id !== id));
                setTotalExpense(prev => prev - transactions.find(t => t.id === id).amount);
            } else if (type === "Income") {
                await IncomeService.deleteIncome(id);
                setIncomeEntries(prev => prev.filter(income => income.incomeId !== id));
                setTransactions(prev => prev.filter(transaction => transaction.id !== id));
                setTotalIncome(prev => prev - transactions.find(t => t.id === id).amount);
            }
        } catch (error) {
            console.error(`Error deleting ${type.toLowerCase()}:`, error);
        }
    };

    const handleActiveBoxChange = (box) => {
        setActiveBox((prev) => (prev === box ? null : box));
    };

    return(
        <div className='dashboard-container'>
            <HelmetProvider>
                <Helmet>
                    <style>
                        {`
                            body {
                                background: url(${bgImage}) no-repeat center center;
                                background-size: cover;
                            }
                        `}
                    </style>
                </Helmet>
            </HelmetProvider>
            <Header activeBox={activeBox} setActiveBox={handleActiveBoxChange}
            addExpenseEntry={addExpenseEntry} addIncomeEntry={addIncomeEntry}
            />
            {activeBox === 'expense' && <TransactionExpense onCancel={() => setActiveBox(null)} addExpenseEntry={addExpenseEntry} />}
            {activeBox === 'income' && <TransactionIncome onCancel={() => setActiveBox(null)} addIncomeEntry={addIncomeEntry}/>}
            <Overview incomeEntries={incomeEntries} expenseEntries={expenseEntries}/>
            <CategoryBox incomeEntries={incomeEntries} expenseEntries={expenseEntries} />
            <ExpenseHistory monthlyData={monthlyData}/>
            <TransactionHistory transactions={transactions} onDelete={handleDelete} />

        </div>
    );
}
export default Dashboard;