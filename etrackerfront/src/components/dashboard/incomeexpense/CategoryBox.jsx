import { FaMoneyBill } from "react-icons/fa";
import './CategoryBox.css';

function CategoryBox({incomeEntries,expenseEntries}) {
    
    const totalIncome = incomeEntries.reduce((sum,entry) => sum + entry.incomeAmount,0);
    const totalExpense = expenseEntries.reduce((sum,entry) => sum+ entry.expensesAmount,0);
    const getIncomePercentage = (amount) => ((amount / totalIncome) * 100).toFixed(2);
    const getExpensePercentage = (amount) => ((amount / totalExpense) * 100).toFixed(2);

    return (
        <div className="ie-box">
            <div className="income-category">
                <h4>Income Category</h4>
                {incomeEntries.map((income) => (
                    <p key={income.incomeId}>
                        <FaMoneyBill className="icon" /> {income.incomeCategory}
                        <span style={{ color: '#429271' }}> ({getIncomePercentage(income.incomeAmount)}%) </span>
                    </p>
                ))}
            </div>
            <div className="expense-category">
                <h4>Expense Category</h4>
                {expenseEntries.map((expenses) => (
                    <p key={expenses.expensesId}>
                        <FaMoneyBill className="icon" /> {expenses.expensesCategory}
                        <span style={{ color: '#964242' }}> ({getExpensePercentage(expenses.expensesAmount)}%) </span>
                    </p>
                ))}
            </div>
        </div>
    );
};
export default CategoryBox