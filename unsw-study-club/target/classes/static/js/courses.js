const apiUrl = "http://localhost:8080/api";
let activityList = document.getElementById("activity-list");

fetch(`${apiUrl}/case`, {
    method: "GET"
})
    // identify status code in response
    .then(res => {
        if(res.status === 200){
            console.log("successfully fetched cases");
            return Promise.resolve(res.json());
        } else {
            return Promise.reject(res.status);
        }
    })

    // if succeed
    .then (res => {
        document.getElementById("loading-icon").style.display = 'none';
        res.forEach(e => {activityList.appendChild(genListItem(e))});
    })

    // if failed
    .catch(errorCode => {
        alert("login failed. ErrorCode: " + errorCode);
    })

function genListItem(object){
    // create element
    let item = document.createElement('div');
    let title = document.createElement('div');
    let subject = document.createElement('div');
    let description = document.createElement('div');
    let date = document.createElement('div');

    // add content

    title.innerText = object.title;
    subject.innerText = object.subject;
    description.innerText = object.description;
    const d = new Date(object.date);
    date.innerText = d.toLocaleString();

    // add action

    item.addEventListener('click', (e) => {
        window.location.href = `/case/${object.title}`;
    });

    [title, subject, description, date].map((ele) => {item.appendChild(ele)});

    // add style

    item.classList.add("activity-item");
    title.classList.add("title");
    subject.classList.add("subject");
    description.classList.add("description");
    date.classList.add("date");


    return item;
}