const apiUrl = "http://localhost:8080/api"
let container = document.getElementById("case-container");
let caseTitle = document.getElementById("case-title").innerText;

fetch(`${apiUrl}/subtitle/${caseTitle}`, {
    method: "GET"
})
    // identify status code
    .then (res => {
        if (res.status === 200){
            console.log("successfully fetched subtitles");
            return Promise.resolve(res.json());
        } else {
            return Promise.reject(res.json());
        }
    })

    // if succeed
    .then (res => {
        console.log(res);
        if(!JSON.stringify(res)){
            document.getElementById("loading-icon").innerText = "Nothing Here :(";
            
        } else {
            res.forEach(obj => addToPart(obj.part, obj));
            document.getElementById("loading-icon").style.display = 'none';
            
        }

    })

    // if error occurs
    .catch (err => {
        alert("fetching subtitles failed");
        console.log(err);
    })


function addToPart(partTitle, subtitleObj){
    let partElement = document.getElementById(partTitle);

    // if part not found, create new
    if (partElement == null){
        let newE = document.createElement('div');
        let title = document.createElement('div');
        let content = document.createElement('div');

        newE.id = partTitle;
        title.innerText = partTitle;

        newE.classList.add("case-part");
        title.classList.add("case-part-title");

        [title, content].map(e => newE.appendChild(e));
        container.appendChild(newE);
        partElement = newE;
    }

    // create new subtitle element
    let subtitle = document.createElement('div');
    let subtitleTitle = document.createElement('div');
    let comments = createCommentList(subtitleObj.comments);

    subtitleTitle.innerText = '> ' + subtitleObj.content;

    subtitle.classList.add("subtitle");
    subtitleTitle.classList.add("subtitle-title");
    comments.style.display = 'none'; // comments not displayed by default

    // add event listener
    subtitleTitle.addEventListener('click', (e) => {
        comments.style.display = (comments.style.display === 'none')? 'block' : 'none';
    });
    

    [subtitleTitle, comments].map(e => subtitle.appendChild(e));
    partElement.children[1].appendChild(subtitle);

}

function createCommentList(commentList){
    let cElement = document.createElement('ul');

    commentList.forEach(c => {
        let cItem = document.createElement('li');
        let content = document.createElement('div');
        let uploader = document.createElement('div');
        let date = document.createElement('div');

        // add content
        content.innerText = c.content;
        uploader.innerText = c.uploader;
        const d = new Date(c.post_date);
        date.innerText = d.toLocaleDateString();

        // add style
        cItem.classList.add('comment');
        uploader.classList.add('uploader');
        date.classList.add('date');
        date.style.textAlign = "right";
        
        [uploader, content, date].map(e => cItem.appendChild(e));
        cElement.appendChild(cItem);
    })
    

    return cElement;
}