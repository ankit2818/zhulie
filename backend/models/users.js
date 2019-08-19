const mongoose = require('mongoose')
const Schema = mongoose.Schema

let user = new Schema({
  email_id: { type: String },
  password: { type: String },
  teams: [{ team_id: String, team_name: String }],
  invites: [{ team_id: String, admin: String, team_name: String }]
})

module.exports = mongoose.model('users', user)
