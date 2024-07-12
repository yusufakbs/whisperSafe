import { Route, Routes } from "react-router-dom";
import HomePage from "./components/HomePage.jsx";
import "./index.js";
import Status from "./components/Status/Status.jsx";
import StatusViewer from "./components/Status/StatusViewer.jsx";

function App() {
  return (
    <div>
      <Routes>
        <Route path="/" element={<HomePage />}></Route>
        <Route path="/status" element={<Status />}></Route>
        <Route path="/status/:userId" element={<StatusViewer />}></Route>
      </Routes>
    </div>
  );
}

export default App;
