import Navbar from './Navbar'
import Body from './Body'
import WidgetsAdder from './Widgets/WidgetsAdder'
import Widget from './Widget'
import { useState } from 'react';
import { useRef } from 'react';
import 'react-dropdown/style.css';

function App() {
  const [showForm, setShowForm] = useState(false);

  const handleForm = event => {
    setShowForm(!showForm);
  }

  const [widgetList, setWidgetList] = useState([]);

  const deleteWidget = (nbr) => {
    console.log(nbr);
    const newList = widgetList.filter((_, i) => i !== nbr);
    console.log(newList)
    setWidgetList(newList);
}

  const widgetName = useRef(null);
  const formSubmit = event => {
    event.preventDefault();
    const nextIndex = widgetList.length
    setWidgetList([
      ...widgetList,
      <Widget key={widgetList.length} name={widgetName.current.value} nbr={nextIndex} functionDelete={deleteWidget}/>
    ]);
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
