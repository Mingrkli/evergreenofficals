<!--Hi Ming, this is Toby from the future...-->
<!--Hi Ming Once again-->
<!--Hi Ming Once again, again...-->
<template>
  <div class="ticket-popup-container">
  <div class="ticket-popup">
    <div class="ticket-list-title">
      <h1>Ticket Create</h1>
      <button class="btn" @click="$emit('ticket-created-popup')">Back</button>
    </div>

    <form @submit.prevent="addTicket">
      <div>
        <label for="name">Ticket Name</label>
        <InputText name="name" v-model="addTicketData.ticket.ticketName" placeholder="Ticket Name"/>
        <p v-if="errorName" class="error">{{errorName}}</p>
      </div>

      <div>
        <label for="name">Your Name</label>
        <InputText name="name" v-model="addTicketData.messageInfo.username" placeholder="Your Name"/>
      </div>

      <div>
        <div class="options">
          <Dropdown v-model="addTicketData.ticket.priority" :options="priorities" placeholder="Select a Priority" class="option" />
          <Dropdown v-model="addTicketData.ticket.type" :options="types" placeholder="Select a Type" class="option" />
        </div>
      </div>

      <div class="message-box">
        <textarea placeholder="Please Describe How We Can Help You..." v-model="addTicketData.messageInfo.message"></textarea>
        <div id="message-box-btns">
          <!--Added click event-->
          <button @click="submitForm" type="submit" id="submit-btn" :disabled="isSubmitDisabled">Submit</button>
        </div>
        <p v-if="errorMessage" class="error">{{errorMessage}}</p>
      </div>
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
  data() {
    return {
      addTicketData: {
        ticket: {
          ticketName: "",
          created: "not implemented",
          lastMessage: "not implemented",
          status: "Open",
          groupID: "not implemented",
          priority: "Low",
          type: "Question",
        },
        messageInfo: {
          message: "",
          created: "",
          username: "",
        },
      },
      errorName: " ",
      errorMessage: " ",
      priorities: ["Low", "Mid", "High", "Critical"],
      types: [ 'Question', 'Bug', 'Feature'],
    };
  },
  watch: {
    'addTicketData.ticket.ticketName': 'validateName',
    'addTicketData.messageInfo.message': 'validateMessage',
  },
  // With computed it automatically run when any of the dependencies they rely on change
  computed: {
    isSubmitDisabled() {
      return this.errorName !== "" || this.errorMessage !== "";
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
    validateMessage(newValue) {
      const character = /[`@#$%^&*()_+\-=[\]{};'"\\|<>/~]/;

      if (newValue.length < 50 || newValue.length > 700) {
        this.errorMessage = "Description must be at least 50 to 700 characters.";
      } else if (character.test(newValue)) {
        this.errorMessage = "Description can't contain special characters.";
      } else {
        this.errorMessage = "";
      }
    },
    // Adds a ticket
    addTicket() {
      // gets the current date in this format MM-DD-YYYY | HH:MM
      const date = new Date();
      const currentDate = `${date.getMonth()}/${date.getDate()}/${date.getFullYear()} ${date.getHours()}:${date.
      getMinutes()}`

      // Sets the created and lastMessage data into the currentDate since we expect the created and lastMessage
      // date to be the current date when the ticket is being created
      this.created = currentDate;
      this.lastMessage = currentDate;

      // We'll save a copy of this.ticket to avoid mutating the original object and apparently it works
      const ticketCopy = {
        ticket: {...this.addTicketData.ticket, created: currentDate, lastMessage: currentDate },
        message: {...this.addTicketData.messageInfo, created:currentDate},
      };
      // Adds the ticket to the database
      fetch("http://localhost:8080/add", {
          method: "POST",
          headers: {
              "Content-Type": "application/json",
          },
          body: JSON.stringify(ticketCopy),
      }).then((data) => {
        if (data.status === 400) {
          data.text().then((errorMessage) => {
            let testing = JSON.parse(errorMessage);
            this.errorName = testing.ticketName;
            this.errorMessage = testing.lastMessage;
          })
        }
        else if (data.status === 200 || data.status === 201) {
          this.$emit('ticket-created');
          this.$emit('ticket-created-popup');
          window.scrollTo(0, document.body.scrollHeight);
        }
        else {
          // Log out other status
          console.error("Error:", data.status);
        }
      });
    },
  },
};
</script>

<style scoped>
  .p-dropdown {
    padding: .3rem .5rem;
  }
</style>