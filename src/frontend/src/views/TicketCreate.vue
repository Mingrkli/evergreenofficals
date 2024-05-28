<!--Hi Ming, this is Toby from the future...-->
<!--Hi Ming Once again-->
<!--Hi Ming Once again, again...-->
<template>
  <div class="ticketCreateContainer">
  <div id="ticket-create">
    <div id="ticket-list-title">
      <h1>Ticket Create</h1>
      <button class="btn" @click="$emit('ticket-created-popup')">Back</button>
    </div>

    <form @submit.prevent="addTicket">
      <div>
        <label for="name">Ticket Name</label>
        <InputText name="name" v-model="addTicketData.ticket.name" placeholder="Ticket Name"/>

        <p v-if="errorName" class="error">{{errorName}}</p>
      </div>

      <div>
        <label for="name">Your Name</label>
        <InputText name="name" v-model="addTicketData.messageInfo.username" placeholder="Your Name"/>
      </div>

      <div>
        <div id="options">

          <Dropdown v-model="addTicketData.ticket.priority" :options="priorities" placeholder="Select a Priority" class="option" />

<!--          <div class="option">-->
<!--            -->

<!--                        <label for="priority">Priority</label>-->
<!--            <select v-model="ticket.priority" name="priority">-->
<!--              <option value="Low">Low</option>-->
<!--              <option value="Mid">Mid</option>-->
<!--              <option value="High">High</option>-->
<!--              <option value="Critical">Critical</option>-->
<!--            </select>-->
<!--          </div>-->

          <Dropdown v-model="addTicketData.ticket.type" :options="types" placeholder="Select a Type" class="option" />

<!--          <div class="option">-->
<!--            <label for="type">Type</label>-->
<!--            <select v-model="ticket.type" name="type">-->
<!--              <option value="Question">Question</option>-->
<!--              <option value="Bug">Bug</option>-->
<!--              <option value="Feature">Feature</option>-->
<!--            </select>-->
<!--          </div>-->
        </div>
      </div>

      <div id="message-box">
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
// import useValidate from "@vuelidate/core";
// import { required } from "@vuelidate/validators";
// import {reactive, computed} from 'vue'

export default {
  data() {
    return {
      addTicketData: {
        ticket: {
          name: "",
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
    'addTicketData.ticket.name': 'validateName',
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

      // TESTING
      // console.log(ticketCopy);

      // Adds the ticket to the database
      fetch("http://localhost:8080/add", {
          method: "POST",
          headers: {
              "Content-Type": "application/json",
          },
          body: JSON.stringify(ticketCopy),
      }).then((data) => {
          // TESTING
          // console.log(data);

        if (data.status === 400) {
          // The user stays in the same page
          // this.error = "Please enter valid name.";

          // console.log(data);
          // console.log(data.text())
          // this.error = JSON.parse(data.text());

          data.text().then((errorMessage) => {
            // this.error = errorMessage;

            // console.log(errorMessage)

            let testing = JSON.parse(errorMessage);
            // console.log(testing)
            this.errorName = testing.name;
            this.errorMessage = testing.lastMessage;
            // console.log(this.lastMessage);
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

        // else {
        //   this.error = "";
        //
        //   // Returns user to home
        //   // this.$router.push("/");
        // }
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