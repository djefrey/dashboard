export default function Body({ userInfos, showForm, setShowForm, widgetList}) {
    const onAddBtnShowForm = event => {
        setShowForm(!showForm);
    }

    return(
        <div className="w-screen h-full flex justify-center">
            <div className="w-10/12 h-full bg-body">
                <div className="w-full h-full flex flex-wrap overflow-y-auto">
                    {
                        userInfos != null &&
                        <div>{widgetList}</div>
                    }
                    {
                        userInfos == null &&
                        <p className="text-xl font-semibold mt-2 text-center text-white w-full">Please log in to access panel</p>
                    }
                    </div>
                </div>
            <div className="w-2/12 h-full bg-navbar text-center">
                <button disabled={userInfos == null} className={(userInfos != null ? "bg-blue-500 hover:bg-blue-700 " : "bg-gray-500 ") + " text-white font-bold py-2 px-4 rounded-full mt-10"} onClick={onAddBtnShowForm}>
                    Add Widget
                </button>
            </div>
        </div>
    ); 
}