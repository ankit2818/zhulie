var express = require('express')
var router = express.Router()
var userSchema = require('../models/users.js')
var mongoose = require('mongoose')

router.post('/register', (req, res) => {
  var user = userSchema({
    'email_id': req.body.email_id,
    'password': req.body.password

  })
  user.save((err, savedUser) => {
    if (err) throw err
    res.json({ success: 'true' })
  })
})

router.post('/login', (req, res) => {
  var user = mongoose.model('users')
  user.findOne({ 'email_id': req.body.email_id, 'password': req.body.password }, (err, user) => {
    if (err) throw err
    if (user != null) {
      res.json({ success: 'true', user: user })
    } else {
      res.json({ success: 'false', message: 'Invalid Email or Password' })
    }
  })
})

module.exports = router
