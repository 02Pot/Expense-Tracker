const sendAllPayslipEmails = async () => {
    try {
        const response = await fetch(`${CONFIG.BASE_URL}/mail/send-all`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Failed to send emails: ${errorText}`);
        }

        const message = await response.text();
        console.log("Success:", message);
        return { success: true, message };

    } catch (error) {
        console.error("Error sending payslip emails:", error.message);
        return { success: false, message: error.message };
    }
};

window.sendPayslipEmail = async (employeeId) => {
    try {
        const response = await fetch(`${CONFIG.BASE_URL}/mail/send/${employeeId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Failed to send email: ${errorText}`);
        }

        const message = await response.text();
        console.log("Success:", message);
        return { success: true, message };

    } catch (error) {
        console.error("Error sending payslip email:", error.message);
        return { success: false, message: error.message };
    }
};