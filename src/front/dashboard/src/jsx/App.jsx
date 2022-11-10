import Navbar from './Navbar'
import Body from './Body'
import WidgetsAdder from './Widgets/WidgetsAdder'
import Widget from './Widgets/Widget'
import WeatherReport from './Widgets/WeatherReport'
import YoutubeStatistics from './/Widgets/YoutubeStatistics'
import RedditStatistics from './/Widgets/RedditStatistics';
import { useState } from 'react';
import 'react-dropdown/style.css';
import PollutionReport from './Widgets/PollutionReport'

function App() {
  const [showForm, setShowForm] = useState(false);

  const [widgetList, setWidgetList] = useState([]);
  const [nextId, setNextId] = useState(0);

  const handleForm = event => {
    setShowForm(!showForm);
  }

  const moveWidgetUp = (toFind) => {
    setWidgetList(widgetList => {
      var position = widgetList.findIndex(widget => { return widget.props.id === toFind });
      if (position === 0)
        return widgetList
      var newList = [...widgetList];
      var tmp = newList[position - 1];
      newList[position - 1] = newList[position];
      newList[position] = tmp
      return newList;
    });
  }

  const moveWidgetDown = (toFind) => {
    setWidgetList(widgetList => {
      var position = widgetList.findIndex(widget => { return widget.props.id === toFind });
      if (position >= widgetList.length - 1)
        return widgetList
      var newList = [...widgetList];
      var tmp = newList[position + 1];
      newList[position + 1] = newList[position];
      newList[position] = tmp
      return newList;
    });
  }

  const deleteWidget = (nbr) => {
    setWidgetList(widgetList => widgetList.filter(widget => widget.props.id !== nbr));
  }

  const formSubmit = (widgetName) => {
    var widget = null;
    switch (widgetName) {
      case "Weather Report":
        widget = <WeatherReport preview={false} />;
        break;
      case "Pollution Report":
        widget = <PollutionReport preview={false} />;
        break;
      case "Youtube Statistics":
        widget = <YoutubeStatistics preview={false} />;
        break;
      case "Reddit Statistics":
        widget = <RedditStatistics preview={false} />;
        break;
      default:
        break;
    }
    setWidgetList(widgetList => ([
      ...widgetList,
      <Widget key={widgetList.length} id={nextId} functionDelete={deleteWidget} functionUp={moveWidgetUp} functionDown={moveWidgetDown} widgetContent={widget} />
    ]));
    setNextId(nextId + 1);
    handleForm();
  }

  return (
    <div className="h-screen overflow-x-hidden">
      {
        showForm &&
        <WidgetsAdder formHandler={handleForm} formSubmit={formSubmit} />
      }
      <Navbar />
      <Body showForm={showForm} setShowForm={setShowForm} widgetList={widgetList} />
    </div>
  );
}

export default App;
