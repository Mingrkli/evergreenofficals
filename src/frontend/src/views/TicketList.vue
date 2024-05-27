<template>
    <div id="ticket-list">
        <div id="ticket-list-title">
            <h1>Ticket List</h1>
            <button type="button" class="btn" @click="toggleTicketCreate">Create</button>
        </div>

<!--        <table>-->
<!--            <thead>-->
<!--                <tr>-->
<!--                    <th>Ticket ID</th>-->
<!--                    <th>Ticket Name</th>-->
<!--                    <th>Created</th>-->
<!--                    <th>Last Message</th>-->
<!--                    <th>Status</th>-->
<!--                    <th>Group ID</th>-->
<!--                    <th>Priority</th>-->
<!--                    <th>Type</th>-->
<!--                </tr>-->
<!--            </thead>-->

<!--            <tbody>-->
<!--                <tr-->
<!--                    v-for="(ticket, index) in tickets"-->
<!--                    :key="ticket.id"-->
<!--                    @click="goToPage(index)"-->
<!--                >-->
<!--                    <td>{{ ticket.id }}</td>-->
<!--                    <td>{{ ticket.name }}</td>-->
<!--                    <td>{{ ticket.created }}</td>-->
<!--                    <td>{{ ticket.lastMessage }}</td>-->
<!--                    <td>{{ ticket.status }}</td>-->
<!--                    <td>{{ ticket.groupID }}</td>-->
<!--                    <td>{{ ticket.priority }}</td>-->
<!--                    <td>{{ ticket.type }}</td>-->
<!--                </tr>-->



<!--                &lt;!&ndash; <tr v-for="index in 10" @click="goToPage(4502)">-->
<!--                    <td>4502</td>-->
<!--                    <td>TICKET SUBJECT HERE</td>-->
<!--                    <td>25 Nov 2024</td>-->
<!--                    <td>27 Nov 2024</td>-->
<!--                    <td>OPEN</td>-->
<!--                    <td>456685442</td>-->
<!--                    <td>Critical</td>-->
<!--                    <td>Critical</td>-->
<!--                </tr> &ndash;&gt;-->
<!--            </tbody>-->
<!--        </table>-->

      <DataTable :value="tickets" removableSort class="dataTable" @row-click="onRowClick">
        <Column field="id" header="Id" sortable></Column>
        <Column field="name" header="Name" sortable></Column>
        <Column field="created" header="Created" sortable></Column>
        <Column field="lastMessage" header="Last Message" sortable></Column>
        <Column field="status" header="Status" sortable></Column>
        <Column field="groupID" header="GroupID" sortable></Column>
        <Column field="priority" header="Priority" sortable></Column>
        <Column field="type" header="Type" sortable></Column>
      </DataTable>

      <TicketCreate v-if="ticketCreateShown" id="ticket-popup" @ticket-created-popup="toggleTicketCreate" @ticket-created="getTickets"/>
    </div>
</template>

<script setup>
// Hook
import { useRouter } from "vue-router";
import DataTable from 'primevue/datatable';
import Column from "primevue/column";


// Gets access to the router
const router = useRouter();

function onRowClick(event) {
  const index = Number(event.data.id);
  goToPage(index - 1);
}

function goToPage(number) {
    router.push({ path: `ticket/${Number(number + 1)}/chat` });
}
</script>

<script>
import TicketCreate from "./TicketCreate.vue";


export default {
    data() {
        return {
            tickets: [],
            ticketCreateShown: false,
        };
    },
    methods: {
        // Fetch all the tickets, save it into tickets array
        getTickets() {
            fetch("https://evergreenofficals-a4332d203a2f.herokuapp.com/tickets")
                .then((res) => res.json())
                .then((data) => {
                    this.tickets = data;

                    // TESTING
                    // console.log(data);
                });
        },
        // Toggle the ticket create popup
      toggleTicketCreate() {
          this.ticketCreateShown = !this.ticketCreateShown;
      }
    },
    beforeMount() {
        this.getTickets();
    },
};
</script>

<style scoped>
#ticket-popup {
  position: fixed;
  top:50%;
  left:50%;
  transform: translate(-50%, -50%);
}
</style>