$(document).ready(function() {
    // Load today's schedule
    loadTodaySchedule();

    // Load recent patients
    loadRecentPatients();

    // Initialize tooltips
    $('[data-bs-toggle="tooltip"]').tooltip();

    // Block Time Form Submit
    $('#confirmBlockTime').click(function() {
        const formData = {
            date: $('#blockTimeForm input[type="date"]').val(),
            startTime: $('#blockTimeForm input[type="time"]').eq(0).val(),
            endTime: $('#blockTimeForm input[type="time"]').eq(1).val(),
            reason: $('#blockTimeForm input[type="text"]').val()
        };

        // Validate form
        if (!validateBlockTimeForm(formData)) {
            alert('Please fill all required fields');
            return;
        }

        // Block time slot
        blockTimeSlot(formData);
    });
});

function loadTodaySchedule() {
    // Simulate API call
    const appointments = [
        {
            id: 1,
            time: '09:00 AM',
            patient: 'John Smith',
            type: 'Check-up',
            status: 'Confirmed'
        },
        {
            id: 2,
            time: '10:30 AM',
            patient: 'Emma Lee',
            type: 'Follow-up',
            status: 'In Progress'
        },
        {
            id: 3,
            time: '11:30 AM',
            patient: 'Michael Brown',
            type: 'Consultation',
            status: 'Waiting'
        }
    ];

    const scheduleTable = $('#todaySchedule');
    scheduleTable.empty();

    appointments.forEach(appointment => {
        const statusClass = getStatusClass(appointment.status);
        scheduleTable.append(`
            <tr>
                <td>${appointment.time}</td>
                <td>${appointment.patient}</td>
                <td>${appointment.type}</td>
                <td><span class="badge ${statusClass}">${appointment.status}</span></td>
                <td>
                    <button class="btn btn-sm btn-outline-primary me-1" onclick="viewAppointment(${appointment.id})">
                        View
                    </button>
                    <button class="btn btn-sm btn-outline-success me-1" onclick="startAppointment(${appointment.id})">
                        Start
                    </button>
                    <button class="btn btn-sm btn-outline-danger" onclick="cancelAppointment(${appointment.id})">
                        Cancel
                    </button>
                </td>
            </tr>
        `);
    });
}

function loadRecentPatients() {
    // Simulate API call
    const patients = [
        {
            id: 1,
            name: 'John Smith',
            lastVisit: '2024-03-10',
            condition: 'Hypertension'
        },
        {
            id: 2,
            name: 'Emma Lee',
            lastVisit: '2024-03-09',
            condition: 'Diabetes'
        },
        {
            id: 3,
            name: 'Michael Brown',
            lastVisit: '2024-03-08',
            condition: 'Arthritis'
        }
    ];

    const patientsList = $('#recentPatients');
    patientsList.empty();

    patients.forEach(patient => {
        patientsList.append(`
            <div class="list-group-item">
                <div class="d-flex w-100 justify-content-between">
                    <h6 class="mb-1">${patient.name}</h6>
                    <small class="text-muted">${patient.lastVisit}</small>
                </div>
                <p class="mb-1">${patient.condition}</p>
                <div class="d-flex justify-content-end">
                    <button class="btn btn-sm btn-link" onclick="viewPatient(${patient.id})">
                        View Details
                    </button>
                </div>
            </div>
        `);
    });
}

function getStatusClass(status) {
    switch (status.toLowerCase()) {
        case 'confirmed':
            return 'bg-success';
        case 'in progress':
            return 'bg-primary';
        case 'waiting':
            return 'bg-warning';
        case 'cancelled':
            return 'bg-danger';
        default:
            return 'bg-secondary';
    }
}

function validateBlockTimeForm(formData) {
    return Object.values(formData).every(value => value !== '' && value !== undefined);
}

function blockTimeSlot(formData) {
    // Simulate API call
    setTimeout(() => {
        alert('Time slot blocked successfully!');
        $('#blockTimeModal').modal('hide');
        loadTodaySchedule();
        $('#blockTimeForm')[0].reset();
    }, 1000);
}

function viewAppointment(id) {
    // Implement view appointment details
    alert('Viewing appointment ' + id);
}

function startAppointment(id) {
    // Implement start appointment
    if (confirm('Start appointment with patient?')) {
        // Update appointment status
        alert('Appointment started');
        loadTodaySchedule();
    }
}

function cancelAppointment(id) {
    // Implement cancel appointment
    if (confirm('Are you sure you want to cancel this appointment?')) {
        // Update appointment status
        alert('Appointment cancelled');
        loadTodaySchedule();
    }
}

function viewPatient(id) {
    // Implement view patient details
    window.location.href = `patient-details.html?id=${id}`;
}