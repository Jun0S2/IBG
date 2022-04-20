import React from "react";
import ReactDOM from "react-dom";
import App from "./App";

//redux
import { createStore } from "redux";
import sessionStorage from "redux-persist/lib/storage/session";
import { persistStore, persistReducer } from "redux-persist";
import { PersistGate } from "redux-persist/integration/react";
import { composeWithDevTools } from "redux-devtools-extension";
import { Provider } from "react-redux";

const persistConfig = {
  key: "root",
  storage: sessionStorage,
};

function reducer(currentState: any, action: any) {
  if (currentState === undefined) {
    return {
      user: {},
      isLogin: false,
    };
  }

  const newState = { ...currentState };

  if (action.type === "login") {
    newState.user = action.userData;
    newState.isLogin = true;
    return newState;
  }

  if (action.type === "logout") {
    newState.user = {};
    newState.isLogin = false;
    return newState;
  }
  if (action.type === "join") {
    //return newState;
    newState.user = action.userData;
    newState.isLogin = true;
  }

  return newState;
}

const persistedReducer = persistReducer(persistConfig, reducer);
const store = createStore(persistedReducer, composeWithDevTools());
const persistor = persistStore(store);

ReactDOM.render(
  <Provider store={store}>
    <PersistGate loading={null} persistor={persistor}>
      <React.StrictMode>
        <App />
      </React.StrictMode>
    </PersistGate>
  </Provider>,
  document.getElementById("root")
);
