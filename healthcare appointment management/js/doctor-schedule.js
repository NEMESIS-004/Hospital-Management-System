$(document).ready(function() {
    // Initialize calendar
    initializeCalendar();

    // Load today's schedule
    loadTodaySchedule();

    // Save schedule settings
    $('#saveScheduleSettings').click(function() {
        saveScheduleSettings();
    });

    // Save working days
    $('#saveWorkingDays').click(function() {
        saveWorkingDays();
    });

    // Initialize tooltips
    $('[data-bs-toggle="tooltip"]').tooltip();
});

function initializeCalendar() {
    const today = new Date();
    const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);
    const lastDay = new Date(today.getFullYear(), today.getMonth() + 1, 0);
    
    const calendarGrid = $('.calendar-grid');
    calendarGrid.empty();

    // Add day headers
    const days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
    days.forEach(day => {
        calendarGrid.append(`
            <div class="calendar-header text-center py-2">${day}</div>
        `);
    });

    // Add empty cells for days before first day of month
    for (let i = 0; i < firstDay.getDay(); i++) {
        calendarGrid.append('<div class="calendar-day empty"></div>');
    }

    // Add days of the month
    for (let i = 1; i <= lastDay.getDate(); i++) {
        const isToday = i === today.getDate();
        const hasAppointments = Math.random() > 0.5; // Simulate appointments
        
        calendarGrid.append(`
            <div class="calendar-day position-relative ${isToday ? 'active' : ''}" 
                 onclick="viewDaySchedule(${i})">
                <span>${i}</span>
                ${hasAppointments ? '<div class="appointment-indicator"></div>' : ''}
            </div>
        `);
    }
}

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
            status: 'Confirmed'
        }
    ];

    const scheduleTable = $('#todaySchedule');
    scheduleTable.empty();

    appointments.forEach(appointment => {
        scheduleTable.append(`
            <tr>
                <td>${appointment.time}</td>
                <td>${appointment.patient}</td>
                <td>${appointment.type}</td>
                <td><span class="badge bg-success">${appointment.status}</span></td>
                <td>
                    <button class="btn btn-sm btn-outline-primary me-1" onclick="viewAppointment(${appointment.id})">
                        View
                    </button>
                    <button class="btn btn-sm btn-outline-danger" onclick="cancelAppointment(${appointment.id})">
                        Cancel
                    </button>
                </td>
            </tr>
        `);
    });
}

function saveScheduleSettings() {
    const settings = {
        startTime: $('input[type="time"]').eq(0).val(),
        endTime: $('input[type="time"]').eq(1).val(),
        appointmentDuration: $('.form-select').val(),
        breakStart: $('input[type="time"]').eq(2).val(),
        breakEnd: $('input[type="time"]').eq(3).val()
    };

    // Simulate API call
    setTimeout(() => {
        alert('Schedule settings saved successfully!');
    }, 1000);
}

function saveWorkingDays() {
    const workingDays = {
        monday: $('#monday').is(':checked'),
        tuesday: $('#tuesday').is(':checked'),
        wednesday: $('#wednesday').is(':checked'),
        thursday: $('#thursday').is(':checked'),
        friday: $('#friday').is(':checked'),
        saturday: $('#saturday').is(':checked'),
        sunday: $('#sunday').is(':checked')
    };

    // Simulate API call
    setTimeout(() => {
        alert('Working days saved successfully!');
    }, 1000);
}

function viewDaySchedule(day) {
    // Implement view day schedule
    alert(`Viewing schedule for day ${day}`);
}

function viewAppointment(id) {
    // Implement view appointment details
    alert('Viewing appointment ' + id);
}

function cancelAppointment(id) {
    // Implement cancel appointment
    if (confirm('Are you sure you want to cancel this appointment?')) {
        alert('Appointment cancelled');
        loadTodaySchedule();
    }
}