import axios from 'axios';

const API_URL = "http://localhost:8080/income";
const getToken = () => localStorage.getItem('token');


export const getAllIncomes = async () => {
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
        throw new Error("Failed to fetch income entries");
    }
};

export const IncomeTransaction = async (incomeDescription,incomeAmount,incomeCategory) => {
    try {
        const response = await axios.post(API_URL
        ,{
            incomeDescription: incomeDescription,
            incomeAmount: incomeAmount,
            incomeCategory: incomeCategory
        },{
            headers:{
                Authorization: `Bearer ${getToken()}`
            }
        }
    );
        return response.data;
    } catch (error) {
        console.error('Login Failed', error.response ? error.response.data : error.message);
        throw new Error("ERRORRRRRRRR");
    }
};
export const deleteIncome = async (id) =>{
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
export default { getAllIncomes,IncomeTransaction,deleteIncome };
