import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import './App.css';
import Dashboard from './components/dashboard/Dashboard';
import LoginForm from './components/loginform/LoginForm';

function App() {
  
    return(
      <>
        <Router>
          <Routes>
            <Route path="/" element={<LoginForm/>} />
            <Route path="/dashboard" element={<Dashboard/>}/>
          </Routes>
        </Router>
      </>
    );
}



export default App
