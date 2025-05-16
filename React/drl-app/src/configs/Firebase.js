import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";
import { getAuth } from "firebase/auth";

const firebaseConfig = {
  apiKey: "AIzaSyCBFFrD2s0ryfJX8BEQyNHqTrV3U8n598o",
  authDomain: "drlapp-web.firebaseapp.com",
  projectId: "drlapp-web",
  storageBucket: "drlapp-web.appspot.com",
  messagingSenderId: "74385232843",
  appId: "1:74385232843:web:386a7bbe53362a5a8c5c4e",
  measurementId: "G-3NDE4E8JXY",
};

const app = initializeApp(firebaseConfig);
const db = getFirestore(app);
const auth = getAuth(app);

export { db, auth };
