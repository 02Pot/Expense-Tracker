import { useLocation, useNavigate } from 'react-router-dom';
import { TransactionExpense, TransactionIncome } from '../transactiondialog/transaction';
import './Header.css';

function Header({ activeBox,setActiveBox, addExpenseEntry, addIncomeEntry}) {
    const history = useNavigate();
    const handleLogout = () => {
        history('/');
    };
    const location = useLocation();
    const username = location.state?.username;

    return (
        <div className="container">
            <div className='Head'>
                <div className='head-img'>
                    <p>Welcome {username.split('@')[0]} !</p>
                </div>
                <div className='head-button'>
                    <button type='button' id='income' onClick={()=> setActiveBox('income')}>Income</button>
                    <button type='button' id='expense' onClick={()=> setActiveBox('expense')}>Expense</button>
                    <button type="button" id='logout' onClick={handleLogout}>Logout</button>
                </div>
            </div>
            {activeBox === 'expense' && <TransactionExpense addExpenseEntry={addExpenseEntry} />}
            {activeBox === 'income' && <TransactionIncome addIncomeEntry={addIncomeEntry} />}
        </div>
    );
}

export default Header;