const mongoose = require('mongoose')
const Schema = mongoose.Schema

let board = new Schema({
  title: { type: String },
  project: { type: String },
  date_of_creation: { type: String },
  cards: []
})

module.exports = mongoose.model('boards', board)
