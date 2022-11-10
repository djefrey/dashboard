export default function Body({ showForm, setShowForm, widgetList}) {
    const onAddBtnShowForm = event => {
        setShowForm(!showForm);
    }

    return(
        <div className="w-screen h-full flex justify-center">
            <div className="w-10/12 h-full bg-body">
                <div className="w-full h-full flex flex-wrap overflow-y-auto">
                    {widgetList}
                </div>
            </div>
            <div className="w-2/12 h-full bg-navbar text-center">
                <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full mt-10" onClick={onAddBtnShowForm}>
                    Add Widget
                </button>
            </div>
        </div>
    );
}