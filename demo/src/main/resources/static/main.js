let xhr = new XMLHttpRequest();
let dropdown = document.getElementById("status");
dropdown.addEventListener("click",setstatus,false);
let inputting = false;
let addbutton = document.getElementById('addfriend');
addbutton.addEventListener('click',addfriend,false);
getFriendlist();


function setstatus(){
    let status = dropdown.options[dropdown.selectedIndex].value;
    console.log(status);
    if (status === "OTHER") {
        let div = document.createElement('div');
        let input = document.createElement('input');
        let submit = document.createElement('button');
        submit.innerText = "submit";
        div.appendChild(input);
        div.appendChild(submit);
        if (!inputting){
            document.body.appendChild(div);
            inputting = true;
        }
        submit.addEventListener('click',function () {
            if (input.value !== ''){
                status = input.value;
                console.log('submitting: ' + status);
                document.body.removeChild(div);
                inputting = false;
                change(status);
            }
        },false)
    }else{
        change(status);
    }
}
function change(status){
    xhr.open('POST', '/change/' + status);
    xhr.onreadystatechange = update;
    xhr.send(null);
}

function update(){
    if (xhr.status === 200){
        if (xhr.readyState === 4){
            let text = xhr.responseText;
            console.log(text);
            document.querySelector('h1').innerText = text;
        }
    }
}

function getFriendlist(){
    xhr.open('GET','/friends');
    xhr.onreadystatechange = showFriends;
    xhr.send(null);
}

function showFriends(){
    if (xhr.status === 200){
        if (xhr.readyState === 4){
            // console.log(navigator.userAgent);
            clearTable();
            let text = JSON.parse(xhr.responseText);
            console.log(text);
            let table = document.getElementById('friends');
            for (let i=0;i<text.length;i++){
                let tr = document.createElement('tr');
                let tdname = document.createElement('td');
                tdname.innerText = text[i].name;
                let tdstatus = document.createElement('td');
                tdstatus.innerText = text[i].statusname;
                tr.appendChild(tdname);
                tr.appendChild(tdstatus);
                tr.className = 'friendlist';
                table.appendChild(tr);
            }
            setTimeout(getFriendlist,20000);
        }
    }
}

function clearTable(){
    let friends = document.querySelectorAll('.friendlist');
    let table = document.getElementById('friends');
    for (let i=0;i<friends.length;i++){
        table.removeChild(friends[i]);
    }
 }

 function addfriend(){
     xhr.open('POST','/addfriend/'+document.getElementById('name').value);
     xhr.send(null);
     getFriendlist();
 }