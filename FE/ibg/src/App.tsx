import { BrowserRouter } from "react-router-dom";
import Router from "./Router";
import { ThemeProvider } from "@emotion/react";
import { CustomTheme } from "./component/CustomTheme";
import CssBaseline from "@mui/material/CssBaseline";
import NavBar from "./component/layout/NavBar";
import ScrollToTop from "./component/ScrollToTop";
import Footer from "./component/layout/Footer";
import ScrollToTopPage from "./component/ScrollToTopPage";

declare global {
  interface Window {
    kakao: any;
  }
}
function App() {
  return (
    <ThemeProvider theme={CustomTheme}>
      <CssBaseline />

      <BrowserRouter>
        <NavBar />
        <div id="ibgContent">
          <ScrollToTopPage />
          <Router />
        </div>
        <ScrollToTop />
        {window.location.pathname === "/survey" ? null : <Footer />}
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;
