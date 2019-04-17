const functions = require('firebase-functions');

// Create and Deploy Your First Cloud Functions
// https://firebase.google.com/docs/functions/write-firebase-functions

exports.helloWorld = functions.https.onRequest((request, response) => {
 response.send("Hello Arad Aghazadeh!");
});

exports.getTimestamp = functions.https.onRequest((request, response) => {
  response.setHeader('Content-Type', 'application/json');
  response.send(JSON.stringify({ timestamp: Date.now() }));
});
