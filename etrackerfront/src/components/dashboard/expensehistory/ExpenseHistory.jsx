import { BarElement, CategoryScale, Legend, LinearScale, Title, Tooltip, } from "chart.js";
import Chart from "chart.js/auto";
import { Bar } from "react-chartjs-2";
import './ExpenseHistory.css';

Chart.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

function ExpenseHistory({monthlyData}){
    const options = {
        responsive:true,
        plugins: {
            legend: {
                labels: {
                    color: '#fff',
                },
            },
        },
        scales: {
            x: {
                ticks: {
                    color: '#fff',
                },
            },
            y: {
                ticks: {
                    color: '#fff',
                },
            },
        },
    };

    return(
        <div className="expense-history">
            <p className="title-history">Expense History</p>
            <div className="graph-bar">
                <div className="">
                    <Bar
                        key={JSON.stringify(monthlyData)}
                        data={{
                            labels: monthlyData.labels,
                            datasets:[
                                {
                                    label:"Income",
                                    data: monthlyData.income,
                                    backgroundColor: " #6D9886",
                                },
                                {
                                    label:"Expense",
                                    data: monthlyData.expense,
                                    backgroundColor: "#986D6D",
                                }
                            ]
                        }}
                        options={options}
                    />
                </div>
            </div>
        </div>
    );
}

export default ExpenseHistory