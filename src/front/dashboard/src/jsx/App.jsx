import Navbar from './Navbar'
import Body from './Body'
import { useState } from 'react';
import Widget from './Widget'
import {useRef} from 'react';

function App() {
  const [showForm, setShowForm] = useState(false);

  const [widgetList, setWidgetList] = useState([]);
  const [nextId, setNextId] = useState(0);
  const widgetName = useRef(null);

  const handleForm = event => {
    setShowForm(!showForm);
  }

  const deleteWidget = (nbr) => {
    setWidgetList(widgetList => widgetList.filter(widget => widget.props.id !== nbr));
  }

  const formSubmit = event => {
    event.preventDefault();
    setWidgetList(widgetList => ([
      ...widgetList,
      <Widget key={widgetList.length} name={widgetName.current.value} id={nextId} functionDelete={deleteWidget}/>
    ]));
    setNextId(nextId + 1);
    handleForm();
  }

  return (
    <div className="h-screen">
      {
        showForm &&
          <div className="absolute w-screen h-screen bg-black/50 z-10">
            <div className="w-3/6 h-min bg-navbar rounded-[10px] text-center m-auto mt-10">
              <div className="flex justify-end mr-10 pt-4">
                <button className="text-3xl text-gray-600" onClick={handleForm}>x</button>
              </div>
              <p className="text-2xl font-semibold text-white">Widget Creation Form</p>
              <div className="w-5/6 m-auto">
                <form onSubmit={formSubmit}>
                  <label>
                    <p className="text-white text-start font-semibold mb-1">Name</p>
                    <input type="text" ref={widgetName} className="bg-body w-full h-10 rounded-[4px] placeholder-font-bold text-white" placeholder="    Name of your Widget"/><br/>
                  </label>
                  <button type="button" onClick={handleForm} className="bg-gray-700 hover:bg-gray-600 text-white font-bold py-2 px-4 rounded-[5px] mt-10 mb-10">
                    Cancel
                  </button>
                  <button type="submit" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-[5px] mt-10 mb-10 ml-4">
                    Submit
                  </button>
                </form>
              </div>
            </div>
          </div>
      }
      <Navbar />
      <Body showForm={showForm} setShowForm={setShowForm} widgetList={widgetList}/>
    </div>
  );
}

export default App;
