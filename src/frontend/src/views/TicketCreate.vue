<!--Hi Ming, this is Toby from the future...-->
<!--Hi Ming Once again-->
<!--Hi Ming Once again, again...-->
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
        <p v-if="errorName" class="error">{{errorName}}</p>
      </div>

      <div>
        <div id="options">
          <div class="option">
            <label for="priority">Priority</label>
            <select v-model="ticket.priority" name="priority">
              <option value="Low">Low</option>
              <option value="Mid">Mid</option>
              <option value="High">High</option>
              <option value="Critical">Critical</option>
            </select>
          </div>

          <div class="option">
            <label for="type">Type</label>
            <select v-model="ticket.type" name="type">
              <option value="Question">Question</option>
              <option value="Bug">Bug</option>
              <option value="Feature">Feature</option>
            </select>
          </div>
        </div>
      </div>

      <div id="message-box">
        <textarea placeholder="Type a message..." v-model="ticket.lastMessage"></textarea>
        <div id="message-box-btns">
          <!--Added click event-->
          <button @click="submitForm" type="submit" id="submit-btn">Submit</button>
        </div>
        <p v-if="errorMessage" class="error">{{errorMessage}}</p>
      </div>
    </form>
  </div>
</template>

<script>
// import useValidate from "@vuelidate/core";
// import { required } from "@vuelidate/validators";
// import {reactive, computed} from 'vue'

export default {
  // setup() {
  //   const state = reactive ({
  //     name: "",
  //     created: "not implemented",
  //     lastMessage: "",
  //     status: "Open",
  //     groupID: "not implemented",
  //     priority: "Low",
  //     type: "Question",
  //   })
  //   const rules = computed(() => {
  //     return {
  //       name: { required },
  //       created: "not implemented",
  //       lastMessage: "",
  //       status: "Open",
  //       groupID: "not implemented",
  //       priority: { required },
  //       type: { required },
  //     }
  //   })
  //   const v$ = useValidate(rules, state)
  //
  //   return {
  //     state,
  //     v$
  //   }
  // },
  //
  data() {
    return {
      ticket: {
        name: "",
        created: "not implemented",
        lastMessage: "not implemented",
        status: "Open",
        groupID: "not implemented",
        priority: "Low",
        type: "Question",
      },
      errorName: "",
      errorMessage: "",
    };
  },
  
  methods: {
    // submitForm() {
    //   alert("Form Succeeded")
    //   this.v$.$validate()
    //   if (!this.v$.$error) {
    //     alert("Form Success")
    //   } else {
    //     alert('Form failed')
    //   }
    // },
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
      const ticketCopy = { ...this.ticket, created: currentDate, lastMessage: currentDate };

      // TESTING
      // console.log(ticketCopy);

      // Adds the ticket to the database
      fetch("https://evergreenofficals-a4332d203a2f.herokuapp.com/add", {
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

          // console.log(data.text())
          // this.error = JSON.parse(data.text());

          data.text().then((errorMessage) => {
            // this.error = errorMessage;

            // console.log(errorMessage)

            let testing = JSON.parse(errorMessage);
            // console.log(testing)
            this.errorName = testing.name;
            this.errorMessage = testing.lastMessage;
            console.log(this.lastMessage);
          })
        }
        if (data.status !== 200) {
          // this.$router.push("/");
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
  .error {
    color: red;
  }
</style>