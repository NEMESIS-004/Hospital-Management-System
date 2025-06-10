$(document).ready(function() {
    // Global variables
    let currentUser = null;
    let userType = null;

    // Show/Hide Login and Register forms
    $('#showRegisterBtn').click(function(e) {
        e.preventDefault();
        $('#loginSection').addClass('d-none');
        $('#registerSection').removeClass('d-none');
    });

    $('#showLoginBtn').click(function(e) {
        e.preventDefault();
        $('#registerSection').addClass('d-none');
        $('#loginSection').removeClass('d-none');
    });

    // Login Form Submit
    $('#loginForm').submit(function(e) {
        e.preventDefault();
        // Simulate login
        currentUser = {
            id: 1,
            name: 'John Doe',
            email: $('#loginForm input[type="email"]').val(),
            type: 'patient' // or 'doctor'
        };
        userType = currentUser.type;
        showDashboard();
    });

    // Register Form Submit
    $('#registerForm').submit(function(e) {
        e.preventDefault();
        // Simulate registration
        currentUser = {
            id: 1,
            name: $('#registerForm input[type="text"]').val(),
            email: $('#registerForm input[type="email"]').val(),
            type: $('#registerForm select').val()
        };
        userType = currentUser.type;
        showDashboard();
    });

    // Show Dashboard based on user type
    function showDashboard() {
        $('#loginSection, #registerSection').addClass('d-none');
        $('#dashboardSection').removeClass('d-none');
        
        if (userType === 'patient') {
            $('#patientDashboard').removeClass('d-none');
            $('#doctorDashboard').addClass('d-none');
            loadPatientAppointments();
        } else {
            $('#doctorDashboard').removeClass('d-none');
            $('#patientDashboard').addClass('d-none');
            loadDoctorAppointments();
        }
    }

    // Load Patient Appointments
    function loadPatientAppointments() {
        // Simulate loading appointments
        const appointments = [
            {
                id: 1,
                doctor: 'Dr. Jane Smith',
                specialization: 'Cardiology',
                date: '2024-03-15',
                time: '10:30 AM',
                status: 'upcoming'
            },
            {
                id: 2,
                doctor: 'Dr. John Doe',
                specialization: 'Neurology',
                date: '2024-03-20',
                time: '2:00 PM',
                status: 'upcoming'
            }
        ];

        const appointmentsList = $('#appointmentsList');
        appointmentsList.empty();

        appointments.forEach(appointment => {
            appointmentsList.append(`
                <div class="col-md-6 mb-4">
                    <div class="card appointment-card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">${appointment.doctor}</h5>
                            <p class="card-text">
                                <small class="text-muted">${appointment.specialization}</small><br>
                                Date: ${appointment.date}<br>
                                Time: ${appointment.time}
                            </p>
                            <div class="d-flex justify-content-end gap-2">
                                <button class="btn btn-outline-danger btn-sm" onclick="cancelAppointment(${appointment.id})">
                                    Cancel
                                </button>
                                <button class="btn btn-outline-primary btn-sm" onclick="rescheduleAppointment(${appointment.id})">
                                    Reschedule
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            `);
        });
    }

    // Load Doctor Appointments
    function loadDoctorAppointments() {
        // Simulate loading appointments
        const appointments = [
            {
                id: 1,
                patient: 'John Smith',
                time: '3:00 PM',
                type: 'Check-up',
                status: 'Confirmed'
            },
            {
                id: 2,
                patient: 'Emma Lee',
                time: '4:30 PM',
                type: 'Follow-up',
                status: 'Pending'
            }
        ];

        const appointmentsList = $('#todayAppointments');
        appointmentsList.empty();

        appointments.forEach(appointment => {
            appointmentsList.append(`
                <tr>
                    <td>${appointment.time}</td>
                    <td>${appointment.patient}</td>
                    <td>${appointment.type}</td>
                    <td><span class="badge bg-primary">${appointment.status}</span></td>
                    <td>
                        <button class="btn btn-sm btn-outline-primary me-1">View</button>
                        <button class="btn btn-sm btn-outline-danger">Cancel</button>
                    </td>
                </tr>
            `);
        });
    }

    // Book Appointment
    $('#bookAppointmentBtn').click(function() {
        $('#bookingModal').modal('show');
    });

    // Confirm Booking
    $('#confirmBooking').click(function() {
        // Simulate booking confirmation
        alert('Appointment booked successfully!');
        $('#bookingModal').modal('hide');
        loadPatientAppointments(); // Reload appointments list
    });

    // Logout
    $('#logoutBtn').click(function() {
        currentUser = null;
        userType = null;
        $('#dashboardSection').addClass('d-none');
        $('#loginSection').removeClass('d-none');
    });

    // Initialize tooltips
    $('[data-bs-toggle="tooltip"]').tooltip();
});

// Cancel Appointment
function cancelAppointment(id) {
    if (confirm('Are you sure you want to cancel this appointment?')) {
        // Simulate cancellation
        alert('Appointment cancelled successfully!');
        loadPatientAppointments();
    }
}

// Reschedule Appointment
function rescheduleAppointment(id) {
    $('#bookingModal').modal('show');
    // Pre-fill form with existing appointment details
}