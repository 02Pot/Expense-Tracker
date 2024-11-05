import { ExpenseTransaction, getAllExpenses } from '@/services/ExpenseService';
import { IncomeTransaction, getAllIncomes } from '@/services/IncomeService';
import { useEffect, useState } from "react";
import CreatableSelect from 'react-select/creatable';
import './Transaction.css';

export function TransactionExpense({addExpenseEntry,onCancel}){
    const [expensesDescription,setExpensesDescription] = useState('');
    const [expensesAmount,setExpensesAmount] = useState('');
    const [expensesCategory,setExpensesCategory] = useState('');
    const [descriptionError, setDescriptionError] = useState('');
    const [amountError, setAmountError] = useState('');
    const [categoryError, setCategoryError] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [value, setValue] = useState();
    const [option, setOptions] = useState([]);
    const [optionPicked,setOptionPicked] = useState('');
    const handleCancel = () => {
        onCancel();
    };

    useEffect(() => {
        const getOptions = async () => {
            setIsLoading(true);
            try {
                const transactions = await getAllExpenses();
                // console.log(transactions)
                const categoriesSet = new Set(
                    transactions
                        .map((transaction) => transaction.expensesCategory)
                        .filter((category) => category)
                );
                const formattedOptions = Array.from(categoriesSet).map((category) => ({
                    value: category,
                    label: category,
                }));

                setOptions(formattedOptions);
            } catch (error) {
                console.error("Error fetching categories:", error);
            } finally {
                setIsLoading(false);
            }
        };
        getOptions();
    }, []);

    const createOption = (label) => {
        const capitalizedLabel = label.charAt(0).toUpperCase() + label.slice(1);
        return {
            label: capitalizedLabel,
            value: capitalizedLabel.toLowerCase().replace(/\W/g, ''),
        };
    };

    const selectStyle ={
        control:(provided) => ({
            ...provided,
            width: "100%",
            boxShadow:"none",
            textAlign:"left"
        }),
        option:(provided,state) => ({...provided,
            color: state.isSelected ? "black" : "white",
            backgroundColor: state.isSelected ? "white" : "#4A527B",
        })
    }

    const handleExpenseTransaction = async (e) => {
        e.preventDefault();
        let isValid = true;
        setDescriptionError('');
        setAmountError('');
        setCategoryError('');

        if (!expensesDescription) {
            setDescriptionError('Description is required.');
            isValid = false;
        }
        const amountValue = Number(expensesAmount);
        if (!expensesAmount || Number(expensesAmount) <= 0 || isNaN(amountValue)) {
            setAmountError('Please input a valid Amount');
            isValid = false;
        }

        if (isValid) {
            try {
                const newExpense = await ExpenseTransaction(expensesDescription, expensesAmount, expensesCategory);
                addExpenseEntry(newExpense);
                
                setExpensesDescription('');
                setExpensesAmount('');
                setExpensesCategory('');
            } catch (error) {
                console.error("Error creating expense:", error);
            }
        }
    };

    return(
        <div className="ie-transaction">
            <p className='transaction-title'>
                Create a new <span style={{ color: 'red' }}>expense</span> transaction
            </p>
            <div className='transaction-desc'>
                <p>Description</p>
                <input type="text" value={expensesDescription} onChange={(e) => setExpensesDescription(e.target.value)}/>
                {descriptionError && <p style={{ color: 'red' }}>{descriptionError}</p>}
                <p>Transaction description (required)</p>
            </div>

            <div className='transaction-desc'>
                <p>Amount</p>
                <input type="text" value={expensesAmount} onChange={(e) => setExpensesAmount(e.target.value)} />
                {amountError && <p style={{ color: 'red' }}>{amountError}</p>}
                <p>Transaction amount (required)</p>
            </div>
            <div className='tr-category'>
                <div className='unang-category'>
                    <p>Category</p>
                    <CreatableSelect
                    isDisabled={isLoading}
                    isLoading={isLoading}
                    options={option}
                    onChange={(newValue) =>{
                        setValue(newValue);
                        setExpensesCategory(newValue.label);
                        setOptionPicked(newValue);
                    }}
                    onCreateOption={(inputValue) => {
                        const newOption = createOption(inputValue);
                        setOptions((prev) => [...prev, newOption]);
                        setValue(newOption);
                        setExpensesCategory(newOption.label);
                        setOptionPicked(newOption);
                    }}
                    value={value}
                    styles={selectStyle}
                    />
                    {categoryError && <p style={{ color: 'red' }}>{categoryError}</p>}
                    <p>Select a category for this transaction.</p>
                </div>
                <div className='pangalawang-category'>
                    <button onClick={handleCancel} >Cancel</button>
                    <button onClick={handleExpenseTransaction} >Create</button>
                </div>
            </div>
        </div>
    );
}

export function TransactionIncome({addIncomeEntry,onCancel}){
    const [incomeDescription,setIncomeDescription] = useState('');
    const [incomeAmount,setIncomeAmount] = useState('');
    const [incomeCategory,setIncomeCategory] = useState('');
    const [descriptionError, setDescriptionError] = useState('');
    const [amountError, setAmountError] = useState('');
    const [categoryError, setCategoryError] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [value, setValue] = useState();
    const [option, setOptions] = useState([]);
    const [optionPicked,setOptionPicked] = useState('');
    const handleCancel = () => {
        onCancel();
    };

    useEffect(() => {
        const getOptions = async () => {
            setIsLoading(true);
            try {
                const transactions = await getAllIncomes();
                // console.log(transactions)
                const categoriesSet = new Set(
                    transactions
                        .map((transaction) => transaction.incomeCategory)
                        .filter((category) => category)
                );
                const formattedOptions = Array.from(categoriesSet).map((category) => ({
                    value: category,
                    label: category,
                }));

                setOptions(formattedOptions);
            } catch (error) {
                console.error("Error fetching categories:", error);
            } finally {
                setIsLoading(false);
            }
        };
        getOptions();
    }, []);

    const createOption = (label) => {
        const capitalizedLabel = label.charAt(0).toUpperCase() + label.slice(1);
        return {
            label: capitalizedLabel,
            value: capitalizedLabel.toLowerCase().replace(/\W/g, ''),
        };
    };


    const selectStyle ={
        control:(provided) => ({
            ...provided,
            width: "100%",
            boxShadow:"none",
            textAlign:"left"
        }),
        option:(provided,state) => ({...provided,
            color: state.isSelected ? "black" : "white",
            backgroundColor: state.isSelected ? "white" : "#4A527B",
        })
    }

    const handleIncomeTransaction = async (e) => {
        e.preventDefault();
        let isValid = true;
        setDescriptionError('');
        setAmountError('');
        setCategoryError('');

        if (!incomeDescription) {
            setDescriptionError('Description is required.');
            isValid = false;
        }
        const amountValue = Number(incomeAmount);
        if (!incomeAmount || Number(incomeAmount) <= 0 || isNaN(amountValue)) {
            setAmountError('Please input a valid Amount');
            isValid = false;
        }
        if (!incomeCategory) {
            setCategoryError('Category is required.');
            isValid = false;
        }
        if (isValid) {
            try {
                const newIncome = await IncomeTransaction(incomeDescription, incomeAmount, incomeCategory);
                addIncomeEntry(newIncome);
    
                setIncomeDescription('');
                setIncomeAmount('');
                setIncomeCategory('');
            } catch (error) {
                setError(error.message);
            }
        }
    }

    return(
        <div className="ie-transaction">
            <p className='transaction-title'>
                Create a new <span style={{ color: 'green' }}>income</span> transaction
            </p>
            <div className='transaction-desc'>
                <p>Description</p>
                <input type="text" value={incomeDescription} onChange={(e) => setIncomeDescription(e.target.value)} />
                {descriptionError && <p style={{ color: 'red' }}>{descriptionError}</p>}
                <p>Transaction description (required)</p>
            </div>
            
            <div className='transaction-desc'>
                <p>Amount</p>
                <input type="text" value={incomeAmount} onChange={(e) => setIncomeAmount(e.target.value)} />
                {amountError && <p style={{ color: 'red' }}>{amountError}</p>}
                <p>Transaction amount (required)</p>
            </div>

            <div className='tr-category'>
                <div className='unang-category'>
                    <p>Category</p>
                    <CreatableSelect
                    isDisabled={isLoading}
                    isLoading={isLoading}
                    options={option}
                    onChange={(newValue) =>{
                        setValue(newValue);
                        setIncomeCategory(newValue.label);
                        setOptionPicked(newValue);
                    }}
                    onCreateOption={(inputValue) => {
                        const newOption = createOption(inputValue);
                        setOptions((prev) => [...prev, newOption]);
                        setValue(newOption);
                        setIncomeCategory(newOption.label);
                        setOptionPicked(newOption);
                    }}
                    value={value}
                    styles={selectStyle}
                    />
                    {categoryError && <p style={{ color: 'red' }}>{categoryError}</p>}
                    <p>Select a category for this transaction.</p>
                </div>
                <div className='pangalawang-category'>
                    <button onClick={handleCancel} >Cancel</button>
                    <button onClick={handleIncomeTransaction}>Create</button>
                </div>
            </div>
        </div>
    );
}