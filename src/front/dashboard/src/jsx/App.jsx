import Navbar from './Navbar'
import Body from './Body'
import WidgetsAdder from './Widgets/WidgetsAdder'
import Widget from './Widget'
import { useState } from 'react';
import { useRef } from 'react';
import 'react-dropdown/style.css';

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
    <div className="h-screen overflow-hidden">
      {
        showForm &&
          <WidgetsAdder formHandler={handleForm}/>
      }
      <Navbar />
      <Body showForm={showForm} setShowForm={setShowForm} widgetList={widgetList}/>
    </div>
  );
}

export default App;
