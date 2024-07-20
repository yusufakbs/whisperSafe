import { Route, Routes } from "react-router-dom";
import HomePage from "./components/HomePage.jsx";
import "./index.js";
import Status from "./components/Status/Status.jsx";
import StatusViewer from "./components/Status/StatusViewer.jsx";
import SignIn from "./components/Register/SignIn.jsx";
import SignUp from "./components/Register/SignUp.jsx";
import Profile from "./components/Profile/Profile.jsx";

function App() {
  return (
    <div>
      <Routes>
        <Route path="/" element={<HomePage />}></Route>
        <Route path="/status" element={<Status />}></Route>
        <Route path="/status/:userId" element={<StatusViewer />}></Route>
        <Route path="/signin" element={<SignIn />}></Route>
        <Route path="/signup" element={<SignUp />}></Route>
        <Route path="/profile" element={<Profile />}></Route>
      </Routes>
    </div>
  );
}

export default App;
