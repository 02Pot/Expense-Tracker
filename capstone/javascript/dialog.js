async function showPayrollDialog(payrollId) {
    if (!window.payrollDialog) {
        console.error("Dialog not loaded yet!");
        return;
    }

    const token = localStorage.getItem("jwtToken");

    try {
        const res = await fetch(`${CONFIG.BASE_URL}/payroll/${payrollId}`, {
            headers: { "Authorization": `Bearer ${token}` }
        });
        // console.log("Fetching payslip for payrollId:", payrollId);

        if (!res.ok) {
            throw new Error("Failed to fetch payslip. Check token or payroll ID.");
        }

        const payslip = await res.json();
        console.log(payslip);
        
        //alert(alert.PopUp)
        window.payrollDialog.dataset.employeeId = payslip.id;

        const earnBody = window.payrollDialog.querySelector("#earnings-body");
        earnBody.innerHTML = "";
        payslip.earnings.forEach(e => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td class="label-col">${e.name}</td>
                <td class="amount-col">${e.amount}</td>
            `;
            earnBody.appendChild(row);
        });

        const dedBody = window.payrollDialog.querySelector("#deductions-body");
        dedBody.innerHTML = "";
        payslip.deductions.forEach(d => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td class="label-col">${d.name}</td>
                <td class="amount-col">${d.amount}</td>
            `;
            dedBody.appendChild(row);
        });

        document.getElementById("emp-name").textContent = payslip.employeeName;
        
        document.getElementById("emp-rate").textContent = payslip.employeeRate ?? 0;
        document.getElementById("doj").textContent = payslip.dateOfJoining || "N/A";
        document.getElementById("emp-type").textContent = payslip.employeeType;
        document.getElementById("emp-dep").textContent = payslip.department;
        document.getElementById("p-period").textContent =
            `${payslip.payrollStartDate} to ${payslip.payrollEndDate}`;
        if (payslip.employeeType === "Regular") {
            document.getElementById("worked-label").textContent = "Worked Days";
            document.getElementById("worked-hrs").textContent = payslip.workedDays ?? 0;
        } else {
            document.getElementById("worked-label").textContent = "Worked Hours";
            document.getElementById("worked-hrs").textContent = payslip.workedHours ?? 0;
        }
        if(payslip.employeeType === "Regular"){
            document.getElementById("pay-label").textContent = "Employee Salary";
            document.getElementById("emp-rate").textContent = payslip.monthlySalary ?? 0;
        }else{
            document.getElementById("pay-label").textContent = "Employee Rate";
            document.getElementById("emp-rate").textContent = payslip.employeeRate ?? 0;
        }
        document.getElementById("gross-pay").textContent = payslip.totalEarnings;
        document.getElementById("total-deductions").textContent = payslip.totalDeductions;
        document.getElementById("net-pay").textContent = payslip.netPay;

        window.payrollDialog.showModal();

    } catch (err) {
        console.error(err);
        alert("Failed to load payroll data.");
    }
}

function loadDialog() {
    fetch("../html/payslip-dialog.html")
        .then(res => res.text())
        .then(html => {
            document.getElementById("dialog-container")
                .insertAdjacentHTML("beforeend", html);

            const dialog = document.getElementById("payroll-dialog");

            dialog.addEventListener("click", (event) => {
                const rect = dialog.getBoundingClientRect();
                const inside =
                    event.clientX >= rect.left &&
                    event.clientX <= rect.right &&
                    event.clientY >= rect.top &&
                    event.clientY <= rect.bottom;

                if (!inside) dialog.close();
            });
        const sendBtn = dialog.querySelector("#dialog-button-email");
        sendBtn.addEventListener("click", () => {
            const employeeId = dialog.dataset.employeeId;

            if (!employeeId) {
                console.error("No employeeId found");
                return;
            }

            sendPayslipEmail(employeeId);
        });
            window.payrollDialog = dialog;
        });
}

document.addEventListener("DOMContentLoaded", () => {
    loadDialog();
    loadIntoTable(`${CONFIG.BASE_URL}/api/employee`, document.querySelector(".emp-table"));
});
