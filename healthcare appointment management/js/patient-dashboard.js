$(document).ready(function() {
    // Load appointments
    loadAppointments();

    // Initialize tooltips
    $('[data-bs-toggle="tooltip"]').tooltip();

    // Book Appointment
    $('#confirmBooking').click(function() {
        const formData = {
            specialization: $('#bookingForm select').eq(0).val(),
            doctor: $('#bookingForm select').eq(1).val(),
            date: $('#bookingForm input[type="date"]').val(),
            time: $('#bookingForm select').eq(2).val(),
            reason: $('#bookingForm textarea').val()
        };

        // Validate form
        if (!validateBookingForm(formData)) {
            alert('Please fill all required fields');
            return;
        }

        // Simulate booking
        bookAppointment(formData);
    });

    // Handle specialization change
    $('#bookingForm select').eq(0).change(function() {
        loadDoctors($(this).val());
    });

    // Handle doctor change
    $('#bookingForm select').eq(1).change(function() {
        loadAvailableSlots($(this).val(), $('#bookingForm input[type="date"]').val());
    });

    // Handle date change
    $('#bookingForm input[type="date"]').change(function() {
        const selectedDoctor = $('#bookingForm select').eq(1).val();
        if (selectedDoctor) {
            loadAvailableSlots(selectedDoctor, $(this).val());
        }
    });
});

function loadAppointments() {
    // Simulate API call
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

function loadDoctors(specialization) {
    // Simulate API call
    const doctors = {
        cardiology: [
            { id: 1, name: 'Dr. Jane Smith' },
            { id: 2, name: 'Dr. Michael Johnson' }
        ],
        neurology: [
            { id: 3, name: 'Dr. John Doe' },
            { id: 4, name: 'Dr. Sarah Wilson' }
        ]
    };

    const doctorSelect = $('#bookingForm select').eq(1);
    doctorSelect.empty().append('<option value="">Select Doctor</option>');

    if (doctors[specialization]) {
        doctors[specialization].forEach(doctor => {
            doctorSelect.append(`<option value="${doctor.id}">${doctor.name}</option>`);
        });
    }
}

function loadAvailableSlots(doctorId, date) {
    // Simulate API call
    const slots = [
        '09:00 AM',
        '09:30 AM',
        '10:00 AM',
        '10:30 AM',
        '11:00 AM',
        '11:30 AM',
        '02:00 PM',
        '02:30 PM',
        '03:00 PM',
        '03:30 PM'
    ];

    const timeSelect = $('#bookingForm select').eq(2);
    timeSelect.empty().append('<option value="">Select Time</option>');

    slots.forEach(slot => {
        timeSelect.append(`<option value="${slot}">${slot}</option>`);
    });
}

function validateBookingForm(formData) {
    return Object.values(formData).every(value => value !== '' && value !== undefined);
}

function bookAppointment(formData) {
    // Simulate API call
    setTimeout(() => {
        alert('Appointment booked successfully!');
        $('#bookingModal').modal('hide');
        loadAppointments();
        $('#bookingForm')[0].reset();
    }, 1000);
}

function cancelAppointment(id) {
    if (confirm('Are you sure you want to cancel this appointment?')) {
        // Simulate API call
        setTimeout(() => {
            alert('Appointment cancelled successfully!');
            loadAppointments();
        }, 1000);
    }
}

function rescheduleAppointment(id) {
    // Simulate getting appointment details
    const appointment = {
        id: id,
        specialization: 'cardiology',
        doctor: 1,
        date: '2024-03-15',
        time: '10:30 AM',
        reason: 'Regular checkup'
    };

    // Pre-fill form
    $('#bookingForm select').eq(0).val(appointment.specialization).trigger('change');
    setTimeout(() => {
        $('#bookingForm select').eq(1).val(appointment.doctor).trigger('change');
        $('#bookingForm input[type="date"]').val(appointment.date);
        $('#bookingForm textarea').val(appointment.reason);
    }, 100);

    $('#bookingModal').modal('show');
}