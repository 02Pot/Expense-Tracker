import axios from 'axios';

const API_URL = "http://localhost:8080/expenses";
const getToken = () => localStorage.getItem('token');

export const getAllExpenses = async () => {
    try {
        const response = await axios.get(API_URL, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
        // console.log('Added Success', response.data);
        return response.data;
    } catch (error) {
        console.error("Error fetching income entries:", error.response ? error.response.data : error.message);
        throw new Error("EROR");
    }
};

export const ExpenseTransaction = async (expensesDescription,expensesAmount,expensesCategory) => {
    try {
        // console.log("Retrieved Token:", token);
        const response = await axios.post(API_URL
        ,{
            expensesDescription: expensesDescription,
            expensesAmount: expensesAmount,
            expensesCategory: expensesCategory
        },{
            headers:{
                Authorization: `Bearer ${getToken()}`
            }
        }
    );
        // console.log('Added Success', response.data);
        return response.data;
    } catch (error) {
        console.error('Failed', error.response ? error.response.data : error.message);
        throw new Error("ERRORRRRRRRR");
    }
};

export const deleteExpense = async (id) =>{
    try {
        const response = await axios.delete(`${API_URL}/${id}`,{
            headers:{
                Authorization: `Bearer ${getToken()}`
            }
        });
        return response.data;
    } catch (error) {
        console.error('Failed',error.response ? error.response.data : error.message);
        throw new Error("EROR");
    }
}

export default { getAllExpenses,ExpenseTransaction,deleteExpense };
