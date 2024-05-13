<!--Hi Ming, this is Toby from the future...-->
<!--Hi Ming Once again-->
<template>
    <div id="ticket-create">
        <div id="ticket-list-title">
            <h1>Ticket Create</h1>
            <router-link to="/" class="btn">Back</router-link>
        </div>

        <form @submit.prevent="addTicket">
            <div>
                <label for="name">Ticket Name</label>
                <input
                    type="text"
                    name="name"
                    placeholder="Ticket Name"
                    v-model="ticket.name"
                />
            </div>

            <div>
                <div id="options">
                    <div class="option">
                        <label for="priority">Priority</label>
                        <select name="priority">
                            <option value="low">Low</option>
                            <option value="mid">Mid</option>
                            <option value="high">High</option>
                            <option value="critical">Critical</option>
                        </select>
                    </div>

                    <div class="option">
                        <label for="type">Type</label>
                        <select name="type">
                            <option value="low">Low</option>
                            <option value="mid">Mid</option>
                            <option value="high">High</option>
                            <option value="critical">Critical</option>
                        </select>
                    </div>
                </div>
            </div>

            <div id="message-box">
                <textarea placeholder="Type a message..."></textarea>
                <div id="message-box-btns">
                    <button type="submit" id="submit-btn">Submit</button>
                </div>
            </div>
        </form>
    </div>
</template>

<script>
export default {
    data() {
        return {
            ticket: {
                name: "",
                created: "not implemented",
                lastMessage: "not implemented",
                status: "not implemented",
                groupID: "not implemented",
                priority: "not implemented",
                type: "not implemented",
            },
        };
    },
    methods: {
        // Adds a ticket
        addTicket() {
            fetch("http://localhost:8080/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(this.ticket),
            }).then((data) => {
                // TESTING
                // console.log(data);

                // Returns user to home
                this.$router.push("/");
            });
        },
    },
};
</script>
