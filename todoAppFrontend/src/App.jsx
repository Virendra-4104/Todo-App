import { Route, Routes } from 'react-router'
import Auth from './components/Auth'
import Task from './components/Task'
function App() {

  return (
    <>
      <Routes>
            <Route path='/' element={<Auth/>}/>
            <Route path='/task' element={<Task/>}/>
        </Routes>
    </>
  )
}

export default App
