<template>
    <div id="ticket-list">
        <div class="ticket-list-title">
            <h1>Ticket List</h1>
            <button type="button" class="btn" @click="toggleTicketCreate">Create</button>
        </div>

      <DataTable :value="tickets" removableSort class="dataTable" @row-click="onRowClick">
        <Column field="id" header="Id" sortable></Column>
        <Column field="name" header="Name" sortable></Column>
        <Column field="created" header="Created" sortable></Column>
        <Column field="lastMessage" header="Last Message" sortable></Column>
        <Column field="status" header="Status" sortable></Column>
        <Column field="groupID" header="GroupID" sortable></Column>
        <Column field="priority" header="Priority" sortable></Column>
        <Column field="type" header="Type" sortable></Column>
        <Column header="Actions">
          <template  #body="slotProps">
            <div id="action-btns">
              <button type="button" class="btn edit-btn" @click="toggleTicketEdit(slotProps.data.id)"><span class="material-symbols-outlined">edit</span></button>
              <button type="button" class="btn delete-btn" @click="deleteTicket(slotProps.data.id)"><span class="material-symbols-outlined">delete</span></button>
            </div>
          </template>
        </Column>
      </DataTable>

      <TicketCreate v-if="ticketCreateShown" @ticket-created-popup="toggleTicketCreate" @ticket-created="getTickets"/>
      <TicketEdit :editID="editID" v-if="ticketEditShown" @ticket-created-popup="toggleTicketEdit" @ticket-created="getTickets"/>
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
import TicketCreate from "../components/TicketCreate.vue";
import TicketEdit from "../components/TicketEdit.vue";

export default {
    data() {
        return {
            tickets: [],
            ticketCreateShown: false,
            ticketEditShown: false,
            editID: "",
        };
    },
    methods: {
        // Fetch all the tickets, save it into tickets array
        getTickets() {
            fetch("http://localhost:8080/tickets")
                .then((res) => res.json())
                .then((data) => {
                    this.tickets = data;

                    // TESTING
                    // console.log(data);
                });
        },
      // Delete ticket
      deleteTicket(id) {
        fetch(`http://localhost:8080/ticket/delete/${id}`, {
          method: "DELETE"
        }).then((data) => {
          this.getTickets();
        })
      },
      // Toggle the ticket create popup
      toggleTicketCreate() {
          this.ticketCreateShown = !this.ticketCreateShown;
      },
      // Toggle the ticket create popup
      toggleTicketEdit(id) {
        this.ticketEditShown = !this.ticketEditShown;
        this.editID = id;
      },
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

#action-btns {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

#action-btns > * {
  transition: 200ms ease-in-out;
}

#action-btns > *:hover {
  filter: brightness(1.5);
}

.delete-btn {
  background-color: #d40000;
}
</style>