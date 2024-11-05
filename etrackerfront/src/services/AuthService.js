import axios from 'axios';

export const authenticateLogin = async (username, password) => {
    try {
        if (!username || !password) {
            throw new Error('Please enter both username and password');
        }
        const response = await axios.post('http://localhost:8080/users/login', {
            userEmail: username,
            userPassword: password
        });
        const token = response.data.token;
        if(token){
            localStorage.setItem('token',token);
        }else {
                throw new Error('Token not received');
            }
        return response.data;
    } catch (error) {
        throw new Error(error.response ? error.response.data : error.message);
    }
};

export const authenticateRegister = async (userEmail,userFirstName,userLastName,userPassword,userNumber) =>{
    try {
        if (!userEmail || !userFirstName || !userLastName || !userPassword || !userNumber) {
            throw new Error('Please fill in all fields.');
        }
        const response = await axios.post('http://localhost:8080/users/register', {
            userEmail,
            userFirstName,
            userLastName,
            userPassword,
            userNumber
        });
        return response.data;
    } catch (error) {
        throw new Error(error.response ? error.response.data : error.message);
    }
};