import logo from './Logo.png'
import Widget from './Widget';

function App() {
  return (
    <div className="h-screen">
      <div className="w-full h-16 bg-navbar text-center flex">
        <div className=" w-3/12 flex">
          <img src={logo} alt="Logo" className="h-full ml-1"/>
          <p className="text-xl m-auto ml-0 font-semibold text-white">Dashboard</p>
        </div>
        <div className="flex ml-32">
          <p className="text-gray-500 m-auto text-sm font-semibold">Homepage</p>
        </div>
      </div>
      <div className="w-screen h-full flex">
        <div className="w-3/12 h-full bg-navbar border-dashed border-t-[1px] border-grey">
        </div>
        <div className="w-7/12 h-full bg-body">
          <div className="w-full h-full ml-14 flex-col overflow-auto">
          </div>
        </div>
        <div className="w-2/12 h-full bg-navbar border-dashed border-t-[1px] border-grey">
          <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full m-auto">
            Button
          </button>
        </div>
      </div>
    </div>
  );
}

export default App;
