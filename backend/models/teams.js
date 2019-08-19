const mongoose = require('mongoose')
const Schema = mongoose.Schema

let team = new Schema({
  team_name: { type: String },
  admin: { type: String },
  team_members: [],
  projects: [{
    title: { type: String },
    date_of_creation: { type: Date, default: Date.now },
    cards: [{
      activities: [{
        activity_name: String,
        tasks: {
          task_name: String,
          checklist: [{
            item: { type: String },
            status: { type: Boolean, default: false } }],
          labels: [String],
          deadline: [String],
          members: [String]
        }
      }]
    }]
  }]
})

module.exports = mongoose.model('teams', team)
