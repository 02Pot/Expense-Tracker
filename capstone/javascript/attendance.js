let currentPage = 0;

function openAttendancePicker() {
    document.getElementById("attendanceFile").click();
}

function toggleAttendanceViews(hasAttendance) {
    const uploadView = document.getElementById("uploadView");
    const statsView  = document.getElementById("statsView");

    if (!uploadView || !statsView) return;

    if (!hasAttendance) {
        uploadView.style.display = "flex";
        statsView.style.display  = "none";
    } else {
        uploadView.style.display = "none";
        statsView.style.display  = "flex";
    }
}

// Load attendance table
async function loadAttendanceTable() {
    const token = localStorage.getItem("jwtToken");

    try {
        const [attendanceRes, payrollRes,employeeRes] = await Promise.all([
            fetch("http://localhost:8081/attendance", {
                headers: { "Authorization": `Bearer ${token}` }
            }),
            fetch("http://localhost:8081/payroll/cutoff", {
                headers: { "Content-Type": "application/json", "Authorization": `Bearer ${token}` }
            }),
            fetch("http://localhost:8081/api/employee", {
                headers: { "Content-Type": "application/json", "Authorization": `Bearer ${token}` }
            })
        ]);
        if (!attendanceRes.ok || !payrollRes.ok || !employeeRes.ok) throw new Error("Failed to fetch data");

        const attendanceData = await attendanceRes.json();
        const payrollData = await payrollRes.json();
        const employeePage = await employeeRes.json();

        toggleAttendanceViews(attendanceData.length > 0);
        await renderStatsView(employeePage.totalElements, payrollData);
    } catch (err) {
        console.error(err);
        toggleAttendanceViews();
    }
}

async function renderStatsView(totalCount,payrolls) {
    const total = totalCount;
    const processId = new Set(
        payrolls
        .filter( p=> p.dateProcessed !== null)
        .map(p=>p.userId)
    );

    const processed = processId.size;
    const failed = total - processed;

    document.getElementById('processedVal').textContent = processed;
    document.getElementById('failedVal').textContent = failed;
    document.getElementById('totalVal').textContent = total;
}

// Handle attendance file upload
document.getElementById("attendanceFile").addEventListener("change", async function(event) {
    const file = event.target.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);

    const token = localStorage.getItem("jwtToken");

    try {
        const res = await fetch("http://localhost:8081/attendance/import", {
            method: "POST",
            headers: { "Authorization": `Bearer ${token}` },
            body: formData
        });

        if (!res.ok) throw new Error("Failed to import attendance");
        //TODO ALERT POPUP
        alert("Attendance imported successfully!");

        await loadAttendanceTable(); // refresh table & toggle views
        event.target.value = "";     // reset input

    } catch (err) {
        console.error(err);
        alert("Error importing attendance: " + err.message);
    }
});

document.getElementById("runPayrollBtn").addEventListener("click", async () => {
    const token = localStorage.getItem("jwtToken");
    try {
        const res = await fetch("http://localhost:8081/payroll/run-cutoff", {
            method: "POST",
            headers: { "Content-Type": "application/json" ,
                "Authorization": `Bearer ${token}`
            }
        });
        const data = await res.json();
        console.log(data)

        alert(data.message); // "Payroll successfully processed for current cutoff."
        location.reload()
    } 
    catch (err) {
        alert("Error running payroll!");
        console.error(err);
    }
});


async function loadIntoTable(url, table,page=0) {
    currentPage = page;
    const tableBody = table.querySelector("tbody");
    
    tableBody.innerHTML = Array(10).fill(`
        <tr class="skeleton-row">
            <td><div class="skeleton"></div></td>
            <td><div class="skeleton"></div></td>
            <td><div class="skeleton"></div></td>
            <td><div class="skeleton"></div></td>
            <td><div class="skeleton skeleton-badge"></div></td>
        </tr>
    `).join("");

    try {
        const token = localStorage.getItem("jwtToken");
        const response = await fetch(`${url}?page=${page}&size=10`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });

        if (!response.ok) throw new Error("Failed to fetch employees");
        
        const pageData = await response.json();
        const employees = pageData.content;

        if (!employees || employees.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="5" style="text-align:center">No employees found.</td></tr>`;
            return;
        }
        
        tableBody.innerHTML = "";
        employees.forEach(emp => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${emp.employeeFirstName} ${emp.employeeLastName}</td>
                <td>${emp.employeeEmail}</td>
                <td>${emp.employeeContactNumber ? 
                    (emp.employeeContactNumber.toString().startsWith("0") ? emp.employeeContactNumber : "0" + emp.employeeContactNumber) : ""}</td>
                <td>${emp.employeeEmploymentType ? emp.employeeEmploymentType.replaceAll("_"," ") : "N/A"}</td>
            `;

            const actionTd = document.createElement("td");
            if (emp.latestPayrollId) {
                const btn = document.createElement("button");
                btn.classList.add("emp-more-btn");
                btn.textContent = "View";
                btn.addEventListener("click", () => showPayrollDialog(emp.latestPayrollId));
                actionTd.appendChild(btn);
            } else {
                actionTd.textContent = "No Payroll Yet";
            }

            tr.appendChild(actionTd);
            tableBody.appendChild(tr);
        });

        renderPagination(pageData.number,pageData.totalPages,url,table);
    } catch (err) {
        console.error(err);
        tableBody.innerHTML = `<tr><td colspan="5">Failed to load employees</td></tr>`;
    }
}

function renderPagination(current, totalPages,url,table) {
    let pagination = document.querySelector(".pagination");
    if (!pagination) {
        pagination = document.createElement("div");
        pagination.className = "pagination";
        table.after(pagination);
    }

    pagination.innerHTML = `
        <button id="prevBtn" ${current === 0 ? "disabled" : ""}>← Prev</button>
        <span>Page ${current + 1} of ${totalPages}</span>
        <button id="nextBtn" ${current + 1 >= totalPages ? "disabled" : ""}>Next →</button>
    `;

    document.getElementById("prevBtn").addEventListener("click", () => loadIntoTable(url,table,currentPage - 1));
    document.getElementById("nextBtn").addEventListener("click", () => loadIntoTable(url,table,currentPage + 1));

}

function changePage(page) {
    currentPage = page;
    const table = document.querySelector(".emp-table")
    loadIntoTable("http://localhost:8081/api/employee", table, currentPage);
}

document.addEventListener("DOMContentLoaded",() => {
    const employeeTable = document.querySelector(".emp-table");
    loadIntoTable("http://localhost:8081/api/employee", employeeTable,0);
    loadAttendanceTable();
})
