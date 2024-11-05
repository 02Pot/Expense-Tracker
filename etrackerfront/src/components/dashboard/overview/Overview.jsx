import { useEffect, useState } from 'react';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import { FaMoneyCheck } from "react-icons/fa";
import { GiExpense } from "react-icons/gi";
import { MdAccountBalance } from "react-icons/md";
import './Overview.css';

function Overview({incomeEntries,expenseEntries}){
    const [filteredIncomeEntries, setFilteredIncomeEntries] = useState([]);
    const [filteredExpenseEntries, setFilteredExpenseEntries] = useState([]);
    const [startDate, setStartDate] = useState(new Date());

    useEffect(() => {
        handleDateChange(startDate);
    }, [incomeEntries,expenseEntries,startDate]);
    
    const handleDateChange = (date) => {
        setStartDate(date);
        
        const selectedMonth = date.getMonth();
        const selectedYear = date.getFullYear();

        const incomeFiltered = incomeEntries.filter((entry) => {
            const entryDate = new Date(entry.createdOn);
            return entryDate.getMonth() === selectedMonth && entryDate.getFullYear() === selectedYear;
        });

        const expenseFiltered = expenseEntries.filter((entry) => {
            const entryDate = new Date(entry.expenseIncurred);
            return entryDate.getMonth() === selectedMonth && entryDate.getFullYear() === selectedYear;
        });

        setFilteredIncomeEntries(incomeFiltered);
        setFilteredExpenseEntries(expenseFiltered);
    };

    const totalIncome = filteredIncomeEntries.reduce((sum,entry) => sum + entry.incomeAmount,0);
    const totalExpense = filteredExpenseEntries.reduce((sum,entry) => sum+ entry.expensesAmount,0);
    const balance = totalIncome - totalExpense;

    return(
    <div className="overview">
        <div className="label-overview">
            <p>Overview</p>
                <div id="month-sort">
                    <DatePicker
                            selected={startDate}
                            onChange={handleDateChange}
                            dateFormat="MMMM - yyyy"
                            showMonthYearPicker
                            placeholderText="Select month"
                        />
                    </div>
                </div>
        <div className="three-container">
            <div className="income-box">
                <FaMoneyCheck className="icon"/>
                <div className="income-amount">
                    <p>Income</p>
                    <p>₱ {totalIncome.toLocaleString()}</p>
                </div>
            </div>

            <div className="expense-box">
                <GiExpense className="icon"/>
                <div className="expense-amount">
                    <p>Expense</p>
                    <p>₱ {totalExpense.toLocaleString()}</p>
                </div>
            </div>

            <div className="balance-box">
                <MdAccountBalance className="icon" />
                <div className="balance-amount">
                    <p>Balance</p>
                    <p>₱ {balance.toLocaleString()}</p>
                </div>
            </div>

        </div>
    </div>
    );
}

export default Overview