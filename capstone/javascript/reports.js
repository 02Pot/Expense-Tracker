let currentPage = 0;
const pageSize = 11;

async function fetchPayroll(page=0) {
    try {
        const token = localStorage.getItem("jwtToken");
        const res = await fetch(`${CONFIG.BASE_URL}/payroll/all?page=${page}&size=${pageSize}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
        });
        if (!res.ok) throw new Error("Failed to get payroll data");
        return await res.json();
    } catch (err) {
        console.error(err);
        return [];
    }
}

async function populatePayrollTable(page=0) {
    currentPage = page;
    const tbody = document.querySelector(".emp-table tbody");
    tbody.innerHTML = Array(10).fill(`
        <tr class="skeleton-row">
            <td><div class="skeleton"></div></td>
            <td><div class="skeleton"></div></td>
            <td><div class="skeleton"></div></td>
            <td><div class="skeleton skeleton-badge"></div></td>
        </tr>
    `).join("");

    const data = await fetchPayroll(page);

    if (!data || data.content.length === 0) {
        tbody.innerHTML = `<tr><td colspan="4" style="text-align:center">No payroll records found.</td></tr>`;
        return;
    }

    tbody.innerHTML = data.content.map(record => `
        <tr>
            <td>${formatCutoff(record.startDate, record.endDate)}</td>
            <td>${formatDate(record.dateProcessed)}</td>
            <td>${formatAmount(record.netPay)}</td>
            <td><span class="badge ${record.status.toLowerCase()}">${record.status}</span></td>
        </tr>
    `).join("");
    
    renderPagination(data.number,data.totalPages);
    populateSummary(data)
}

async function populateSummary(data) {
    const records = data.content;
    const totalGrossPay = records.reduce((sum, r) => sum + (r.grossPay ?? 0), 0);
    const totalNetPay = records.reduce((sum, r) => sum + (r.netPay ?? 0), 0);
    const totalLogs = data.totalElements;

    document.getElementById("totalGrossPay").textContent = formatAmount(totalGrossPay);
    document.getElementById("totalNetPay").textContent = formatAmount(totalNetPay);
    document.getElementById("totalLogs").textContent = totalLogs;
}

function renderPagination(current, totalPages) {
    let pagination = document.querySelector(".pagination");
    if (!pagination) {
        pagination = document.createElement("div");
        pagination.className = "pagination";
        document.querySelector(".emp-table").after(pagination);
    }

    const prevBtn = `<button id="prevBtn" ${current === 0 ? "disabled" : ""}>← Prev</button>`;
    const nextBtn = `<button id="nextBtn" ${current + 1 >= totalPages ? "disabled" : ""}>Next →</button>`;

    pagination.innerHTML = `
        ${prevBtn}
        <span>Page ${current + 1} of ${totalPages}</span>
        ${nextBtn}
    `;

    document.getElementById("prevBtn").addEventListener("click", () => populatePayrollTable(currentPage - 1));
    document.getElementById("nextBtn").addEventListener("click", () => populatePayrollTable(currentPage + 1));

}

function changePage(page) {
    currentPage = page;
    populatePayrollTable(currentPage);
}

function formatCutoff(start, end) {
    if (!start || !end) return "—";
    const s = new Date(start + "T00:00:00");
    const e = new Date(end + "T00:00:00");
    const month = s.toLocaleDateString("en-US", { month: "short" });
    const year = s.getFullYear();
    return `${month} ${s.getDate()} – ${e.getDate()}, ${year}`;
}

function formatDate(dateStr) {
    if (!dateStr) return "—";
    const date = new Date(dateStr);
    return date.toLocaleDateString("en-US", { month: "short", day: "numeric", year: "numeric" });
}

function formatAmount(amount) {
    if (amount == null) return "—";
    return "P" + parseFloat(amount).toLocaleString("en-PH", {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    });
}

function blurMoney(formattedAmount) {
    return `<span class="money-blur" title="Click to reveal">${formattedAmount}</span>`;
}

function attachBlurToggles() {
    document.querySelectorAll(".money-blur").forEach(el => {
        el.removeEventListener("click", handleBlurToggle);
        el.addEventListener("click", handleBlurToggle);
    });
}

function handleBlurToggle() {
    this.classList.toggle("revealed");
}

document.addEventListener("DOMContentLoaded",() => populatePayrollTable(0));