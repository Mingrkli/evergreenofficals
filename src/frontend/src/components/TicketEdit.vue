<template>
  <div class="ticket-popup-container">
    <div class="ticket-popup">
      <div class="ticket-list-title">
        <h1>Ticket Edit</h1>
        <button class="btn" @click="$emit('ticket-created-popup')">Back</button>
      </div>

      <form @submit.prevent="addTicket">
        <div>
          <label for="name">Ticket Name</label>
          <InputText v-model="ticket.name" name="name" placeholder="Ticket Name"/>
          <p v-if="errorName" class="error">{{errorName}}</p>
        </div>

        <div>
          <label for="groupID">Ticket Group ID</label>
          <InputText v-model="ticket.groupID" name="groupID" placeholder="Ticket GroupID"/>
        </div>

        <div>
          <div class="options">
            <Dropdown v-model="ticket.status" :options="status" placeholder="Select a Status" class="option" />
            <Dropdown v-model="ticket.priority" :options="priorities" placeholder="Select a Priority" class="option" />
            <Dropdown v-model="ticket.type" :options="types" placeholder="Select a Type" class="option" />
          </div>
        </div>

        <button class="btn" @click="submitForm" type="submit" id="submit-btn" :disabled="isSubmitDisabled">Submit</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import InputText from "primevue/inputtext";
import Dropdown from 'primevue/dropdown';
</script>

<script>
export default {
  props: ["editID"],
  data() {
    return {
      ticket: {
        name: "",
        created: "",
        lastMessage: "",
        status: "",
        groupID: "",
        priority: "",
        type: "",
      },
      errorName: " ",
      priorities: ["Low", "Mid", "High", "Critical"],
      types: [ 'Question', 'Bug', 'Feature'],
      status: ["Open", "Close"]
    };
  },
  watch: {
    'ticket.name': 'validateName',
  },
  // With computed it automatically run when any of the dependencies they rely on change
  computed: {
    isSubmitDisabled() {
      return this.errorName !== "";
    }
  },
  methods: {
    validateName(newValue) {
      const character = /[`@#$%^&*()_+\-=[\]{};'"\\|<>/~]/;

      if (newValue.length <= 4) {
        this.errorName = "The ticket name must be longer than 4 characters.";
      } else if (character.test(newValue)) {
        this.errorName = "The ticket name can't contain special characters.";
      } else {
        this.errorName = "";
      }
    },
    // This will get the current ticket information so that we can use it on the aside
    getTicketById(id) {
      fetch(`http://localhost:8080/ticket/${id}`)
          .then((res) => res.json())
          .then((data) => {
            this.ticket = data;
          });
    },
  },
  beforeMount() {
    this.getTicketById(this.editID);
  },
}
</script>