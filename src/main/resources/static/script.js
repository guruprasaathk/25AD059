/* =====================================================
   HABITFORCE FRONTEND
===================================================== */


/* =========================
   GLOBAL VARIABLES
========================= */

let habits = [];

let selectedHabitId = null;


/* =========================
   PAGE LOAD
========================= */

document.addEventListener("DOMContentLoaded", function () {

    loadHabits();

});


/* =====================================================
   LOAD HABITS
===================================================== */

async function loadHabits() {

    const loading = document.getElementById("loading");

    const container = document.getElementById("habitContainer");

    const emptyState = document.getElementById("emptyState");

    loading.classList.remove("hidden");

    container.innerHTML = "";

    emptyState.classList.add("hidden");

    try {

        const response = await fetch("/api/habits");

        if (!response.ok) {

            throw new Error("Failed to load habits");

        }

        const data = await response.json();


        /*
         * Supports both:
         *
         * List<HabitResponse>
         *
         * and
         *
         * Page<HabitResponse>
         */

        if (Array.isArray(data)) {

            habits = data;

        } else if (data.content) {

            habits = data.content;

        } else {

            habits = [];

        }


        loading.classList.add("hidden");


        if (habits.length === 0) {

            emptyState.classList.remove("hidden");

            updateStats();

            return;
        }


        renderHabits();

        updateStats();

    } catch (error) {

        console.error("Error loading habits:", error);

        loading.classList.add("hidden");

        container.innerHTML = `
            <div class="empty-state">
                <div class="empty-icon">⚠️</div>
                <h2>Unable to load habits</h2>
                <p>Make sure your Spring Boot application is running.</p>
                <button onclick="loadHabits()">Try Again</button>
            </div>
        `;

    }

}


/* =====================================================
   RENDER HABITS
===================================================== */

async function renderHabits() {

    const container =
        document.getElementById("habitContainer");

    container.innerHTML = "";


    for (const habit of habits) {

        let streak = {
            currentStreak: 0,
            bestStreak: 0
        };


        try {

            const response =
                await fetch(`/api/streaks/${habit.id}`);

            if (response.ok) {

                const data = await response.json();

                streak.currentStreak =
                    data.currentStreak ??
                    data.currentStreakDays ??
                    0;

                streak.bestStreak =
                    data.bestStreak ??
                    data.bestStreakDays ??
                    0;

            }

        } catch (error) {

            console.log(
                "Streak unavailable for habit:",
                habit.id
            );

        }


        container.innerHTML += createHabitCard(
            habit,
            streak
        );

    }

}


/* =====================================================
   CREATE HABIT CARD
===================================================== */

function createHabitCard(habit, streak) {

    const emoji = getHabitEmoji(habit.name);

    const active =
        habit.active !== false;


    return `

        <div class="habit-card">

            <div class="habit-top">

                <div class="habit-title-area">

                    <div class="habit-emoji">
                        ${emoji}
                    </div>

                    <div>

                        <div class="habit-name">
                            ${escapeHtml(habit.name)}
                        </div>

                        <div class="habit-description">
                            ${escapeHtml(
        habit.description || "No description"
    )}
                        </div>

                    </div>

                </div>


                <span class="status ${active ? "" : "inactive"}">

                    ${active ? "ACTIVE" : "INACTIVE"}

                </span>

            </div>


            <div class="habit-info">

                <div class="info-item">

                    <span>Frequency</span>

                    <strong>
                        ${habit.frequency || "DAILY"}
                    </strong>

                </div>

                <div class="info-item">

                    <span>Habit ID</span>

                    <strong>
                        #${habit.id}
                    </strong>

                </div>

            </div>


            <div class="streak-area">

                <div class="streak-item">

                    🔥

                    <span>
                        Current:
                        ${streak.currentStreak} days
                    </span>

                </div>


                <div class="streak-item">

                    🏆

                    <span>
                        Best:
                        ${streak.bestStreak} days
                    </span>

                </div>

            </div>


            <div class="habit-actions">

                <button
                    class="action-button complete-button"
                    onclick="completeHabit(${habit.id})"
                >
                    ✓ Complete Today
                </button>


                <button
                    class="action-button reminder-button"
                    onclick="openReminderModal(${habit.id})"
                >
                    ⏰ Reminder
                </button>


                <button
                    class="action-button delete-button"
                    onclick="deleteHabit(${habit.id})"
                >
                    Delete
                </button>

            </div>

        </div>

    `;

}


/* =====================================================
   ADD HABIT MODAL
===================================================== */

function openAddHabitModal() {

    document
        .getElementById("habitModal")
        .classList.add("show");

}


function closeAddHabitModal() {

    document
        .getElementById("habitModal")
        .classList.remove("show");

    document
        .getElementById("habitForm")
        .reset();

}


/* =====================================================
   CREATE HABIT
===================================================== */

document
    .getElementById("habitForm")
    .addEventListener("submit", async function (event) {

        event.preventDefault();


        const name =
            document.getElementById("habitName").value.trim();

        const description =
            document
                .getElementById("habitDescription")
                .value
                .trim();

        const frequency =
            document
                .getElementById("habitFrequency")
                .value;


        if (!name) {

            showToast(
                "Please enter a habit name",
                "error"
            );

            return;

        }


        try {

            const response = await fetch("/api/habits", {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({

                    name: name,

                    description: description,

                    frequency: frequency

                })

            });


            if (!response.ok) {

                const error =
                    await response.text();

                console.error(error);

                throw new Error(
                    "Failed to create habit"
                );

            }


            closeAddHabitModal();

            showToast(
                "Habit created successfully!",
                "success"
            );


            await loadHabits();


        } catch (error) {

            console.error(error);

            showToast(
                "Failed to create habit",
                "error"
            );

        }

    });


/* =====================================================
   COMPLETE HABIT
===================================================== */

async function completeHabit(habitId) {

    const today =
        new Date().toISOString().split("T")[0];


    try {

        /*
         * Your CompletionLog backend uses:
         * habitId
         * completionDate
         */

        const response = await fetch(
            "/api/completions",
            {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({

                    habitId: habitId,

                    completionDate: today

                })

            }
        );


        if (!response.ok) {

            const error =
                await response.text();

            console.error(
                "Completion error:",
                error
            );

            throw new Error(
                "Failed to complete habit"
            );

        }


        showToast(
            "Habit completed for today! 🔥",
            "success"
        );


        await loadHabits();


    } catch (error) {

        console.error(error);

        showToast(
            "Failed to complete habit",
            "error"
        );

    }

}


/* =====================================================
   REMINDER MODAL
===================================================== */

function openReminderModal(habitId) {

    selectedHabitId = habitId;


    document
        .getElementById("reminderTime")
        .value = "";


    document
        .getElementById("reminderModal")
        .classList.add("show");

}


function closeReminderModal() {

    document
        .getElementById("reminderModal")
        .classList.remove("show");


    selectedHabitId = null;

}


/* =====================================================
   SAVE REMINDER
===================================================== */

async function saveReminder() {

    const time =
        document
            .getElementById("reminderTime")
            .value;


    if (!selectedHabitId) {

        showToast(
            "Habit ID is missing",
            "error"
        );

        return;

    }


    if (!time) {

        showToast(
            "Please select a reminder time",
            "error"
        );

        return;

    }


    try {

        /*
         * LocalTime in Spring Boot expects:
         *
         * HH:mm:ss
         *
         * Example:
         * 15:55:00
         */

        const reminderTime =
            time.length === 5
                ? time + ":00"
                : time;


        const response = await fetch(
            "/api/reminders",
            {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({

                    habitId: selectedHabitId,

                    reminderTime: reminderTime,

                    enabled: true

                })

            }
        );


        if (!response.ok) {

            const error =
                await response.text();

            console.error(
                "Reminder backend error:",
                error
            );

            throw new Error(
                "Failed to save reminder"
            );

        }


        const reminder =
            await response.json();


        console.log(
            "Reminder saved:",
            reminder
        );


        closeReminderModal();


        showToast(
            "Reminder saved successfully! ⏰",
            "success"
        );


    } catch (error) {

        console.error(
            "Reminder error:",
            error
        );


        showToast(
            "Failed to save reminder",
            "error"
        );

    }

}


/* =====================================================
   DELETE HABIT
===================================================== */

async function deleteHabit(habitId) {

    const confirmed =
        confirm(
            "Are you sure you want to delete this habit?"
        );


    if (!confirmed) {

        return;

    }


    try {

        const response = await fetch(
            `/api/habits/${habitId}`,
            {
                method: "DELETE"
            }
        );


        if (!response.ok) {

            const error =
                await response.text();

            console.error(
                "Delete error:",
                error
            );

            throw new Error(
                "Failed to delete habit"
            );

        }


        showToast(
            "Habit deleted successfully",
            "success"
        );


        await loadHabits();


    } catch (error) {

        console.error(error);

        showToast(
            "Failed to delete habit",
            "error"
        );

    }

}


/* =====================================================
   UPDATE STATISTICS
===================================================== */

function updateStats() {

    const total =
        habits.length;


    document
        .getElementById("totalHabits")
        .textContent = total;


    /*
     * Active habits
     */

    const activeHabits =
        habits.filter(
            habit => habit.active !== false
        );


    document
        .getElementById("activeStreaks")
        .textContent = activeHabits.length;


    /*
     * These are updated when completion
     * information is available.
     */

    document
        .getElementById("completedToday")
        .textContent = "0";


    document
        .getElementById("bestStreak")
        .textContent = "0 days";

}


/* =====================================================
   NAVIGATION
===================================================== */

function showDashboard() {

    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });

}


function scrollToHabits() {

    document
        .getElementById("habitsSection")
        .scrollIntoView({
            behavior: "smooth"
        });

}


/* =====================================================
   TOAST
===================================================== */

let toastTimer;


function showToast(message, type = "success") {

    const toast =
        document.getElementById("toast");

    const messageElement =
        document.getElementById("toastMessage");

    const icon =
        document.getElementById("toastIcon");


    messageElement.textContent =
        message;


    if (type === "error") {

        icon.textContent = "⚠";

        icon.style.background = "#d64545";

    } else {

        icon.textContent = "✓";

        icon.style.background = "#27ae60";

    }


    toast.classList.add("show");


    clearTimeout(toastTimer);


    toastTimer = setTimeout(
        function () {

            toast.classList.remove("show");

        },
        3000
    );

}


/* =====================================================
   HABIT EMOJI
===================================================== */

function getHabitEmoji(name) {

    const text =
        name.toLowerCase();


    if (
        text.includes("exercise") ||
        text.includes("workout") ||
        text.includes("gym") ||
        text.includes("run")
    ) {

        return "🏃";

    }


    if (
        text.includes("book") ||
        text.includes("read") ||
        text.includes("study")
    ) {

        return "📚";

    }


    if (
        text.includes("water") ||
        text.includes("drink")
    ) {

        return "💧";

    }


    if (
        text.includes("sleep") ||
        text.includes("bed")
    ) {

        return "😴";

    }


    if (
        text.includes("food") ||
        text.includes("diet")
    ) {

        return "🥗";

    }


    if (
        text.includes("meditat") ||
        text.includes("mind")
    ) {

        return "🧘";

    }


    return "🌱";

}


/* =====================================================
   HTML SECURITY
===================================================== */

function escapeHtml(value) {

    if (value === null || value === undefined) {

        return "";

    }


    return String(value)

        .replace(/&/g, "&amp;")

        .replace(/</g, "&lt;")

        .replace(/>/g, "&gt;")

        .replace(/"/g, "&quot;")

        .replace(/'/g, "&#039;");

}


/* =====================================================
   CLOSE MODALS WHEN CLICKING OUTSIDE
===================================================== */

window.addEventListener(
    "click",
    function (event) {

        const habitModal =
            document.getElementById("habitModal");

        const reminderModal =
            document.getElementById("reminderModal");


        if (event.target === habitModal) {

            closeAddHabitModal();

        }


        if (event.target === reminderModal) {

            closeReminderModal();

        }

    }
);


/* =====================================================
   ESC KEY CLOSE MODAL
===================================================== */

document.addEventListener(
    "keydown",
    function (event) {

        if (event.key === "Escape") {

            closeAddHabitModal();

            closeReminderModal();

        }

    }
);