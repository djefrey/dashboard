import Navbar from './Navbar'
import Body from './Body'
import WidgetsAdder from './Widgets/WidgetsAdder'
import Widget from './Widgets/Widget'
import WeatherReport from './Widgets/WeatherReport'
import YoutubeStatistics from './/Widgets/YoutubeStatistics'
import RedditStatistics from './/Widgets/RedditStatistics';
import { useState } from 'react';
import 'react-dropdown/style.css';

function App() {
  const [showForm, setShowForm] = useState(false);

  const [widgetList, setWidgetList] = useState([]);
  const [nextId, setNextId] = useState(0);

  const handleForm = event => {
    setShowForm(!showForm);
  }

  const deleteWidget = (nbr) => {
    setWidgetList(widgetList => widgetList.filter(widget => widget.props.id !== nbr));
  }

  const formSubmit = (widgetName) => {
    var widget = null;
    switch (widgetName) {
      case "Weather Report":
          widget = <WeatherReport/>;
          break;
      case "Youtube Statistics":
          widget = <YoutubeStatistics/>;
          break;
      case "Reddit Statistics":
          widget = <RedditStatistics/>;
          break;
      default:
          break;
    }
    setWidgetList(widgetList => ([
      ...widgetList,
      <Widget key={widgetList.length} widgetContent ={widget} id={nextId} functionDelete={deleteWidget}/>
    ]));
    setNextId(nextId + 1);
    handleForm();
  }

  return (
    <div className="h-screen overflow-x-hidden">
      {
        showForm &&
          <WidgetsAdder formHandler={handleForm} formSubmit={formSubmit}/>
      }
      <Navbar />
      <Body showForm={showForm} setShowForm={setShowForm} widgetList={widgetList}/>
    </div>
  );
}

export default App;
