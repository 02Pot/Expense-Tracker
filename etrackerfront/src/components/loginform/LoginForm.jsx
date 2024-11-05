import { useState } from "react";
import { Helmet, HelmetProvider } from "react-helmet-async";
import { FaEnvelope, FaFacebook, FaGoogle, FaLinkedin, FaLock, FaPhone, FaTwitter, FaUser } from "react-icons/fa";
import { useNavigate } from 'react-router-dom';
import bgImage from '../../assets/bg.jpg';
import { authenticateLogin, authenticateRegister } from "../../services/AuthService";
import './LoginForm.css';

function LoginForm(){
    //Switching to Register
    const [action,setAction] = useState('');
    const registerLink =(e)=>{
        e.preventDefault();
        setAction('active');
    };
    const loginLink =(e)=>{
        e.preventDefault();
        setAction('');
    };
    //error n navigate
    const [error,setError] = useState('');
    const history = useNavigate();

    //Login
    const [username,setUsername] = useState('');
    const [password,setPassword] = useState('');
    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            await authenticateLogin(username,password);
            history('/dashboard',{state:{username}});
        } catch (error) {
            setError('Invalid username or password');
        }
    }
    
    //Register
    const [userEmail,setUserEmail] = useState('');
    const [userFirstName,setUserFirstName] = useState('');
    const [userLastName,setUserLastName] = useState('');
    const [userPassword,setUserPassword] = useState('');
    const [userNumber,setNumber] = useState('');
    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            await authenticateRegister(userEmail,userFirstName,userLastName,userPassword,userNumber);
            setAction('');
        } catch (error) {
            setError(error.message);
        }
    }

    return(
        <>
        <HelmetProvider>
            <Helmet>
                <style>
                    {`
                        body {
                            background: url(${bgImage}) no-repeat center center;
                            background-size: cover;
                            min-height: 100vh;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            color: #ffffff;
                            font-family: 'Poppins', sans-serif;
                        }
                    `}
                </style>
            </Helmet>
        </HelmetProvider>
        <div className={`wrapper ${action}`}>
            <div className="form-box login">
                <form action="">
                    <h1>Login</h1>
                    <div className="input-box">
                        <input type="email" placeholder="Email" value={username}
                            onChange={(e) => setUsername(e.target.value)} required />
                        <FaUser className="icon"/>
                    </div>
                    <div className="input-box">
                        <input type="password" placeholder="Password" value={password}
                            onChange={(e) => setPassword(e.target.value)} required />
                        <FaLock className="icon"/>
                    </div>
                    <div className="remember-forgot">
                        <label><input type="checkbox" />Remember me</label>
                        <a href="#">Forgot Password?</a>
                    </div>
                    {error && <p className="text-danger">{error}</p>}
                    <button type="submit" onClick={handleLogin}>Login</button>
                    <div className="register-link">
                        <p>Dont have a account?<a href="" onClick={registerLink}>Register</a></p>
                    </div>
                </form>
            </div>

            <div className="form-box register">
                <form action="">
                    <h1>Register</h1>
                    <div className="input-box">
                        <input type="email" placeholder="Email" name="username" value={userEmail}
                            onChange={(e) => setUserEmail(e.target.value)} required/>
                        <FaEnvelope className="icon"/>
                    </div>
                    <div className="input-box">
                        <input type="text" placeholder="First Name" value={userFirstName}
                            onChange={(e) => setUserFirstName(e.target.value)} required/>
                        <FaUser className="icon"/>
                    </div>
                    <div className="input-box">
                        <input type="text" placeholder="Last Name" value={userLastName}
                            onChange={(e) => setUserLastName(e.target.value)} required/>
                        <FaUser className="icon"/>
                    </div>
                    <div className="input-box">
                        <input type="password" placeholder="Password" name="password" value={userPassword}
                            onChange={(e) => setUserPassword(e.target.value)} required/>
                        <FaLock className="icon" />
                    </div>
                    <div className="input-box">
                        <input type="number" placeholder="Phone Number (+63)" value={userNumber}
                            onChange={(e) => setNumber(e.target.value)} required/>
                        <FaPhone className="icon"/>
                    </div>
                    {error && <p className="text-danger">{error}</p>}
                    <button type="submit" onClick={handleRegister}>Register</button>
                    <div className="social-link">
                        <p>Or Register with social platform</p>
                        <a href="#">
                            <FaFacebook className="icon" />
                        </a>
                        <a href="#" >
                            <FaTwitter  className="icon"/>
                        </a>
                        <a href="#">
                            <FaGoogle className="icon" />
                        </a>
                        <a href="#">
                            <FaLinkedin className="icon" />
                        </a>
                    </div>
                    <div className="register-link">
                        <p>Already Have a Account?<a href="" onClick={loginLink}>Login</a></p>
                    </div>
                </form>
            </div>
        </div>
    </>
    );
}

export default LoginForm;