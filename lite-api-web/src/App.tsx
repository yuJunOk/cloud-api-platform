import {useRoutes} from "react-router-dom";
import {routes} from "@/router";

function App() {
    return (
        <>
            {/*路由*/}
            {useRoutes(routes)}
        </>
  )
}

export default App
