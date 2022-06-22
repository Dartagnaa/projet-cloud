const baseEndpointURL = "https://ocmaco.ew.r.appspot.com/glogin.html/_ah/api/myApi/v1/";

const EndpointURL = {
  login: {
    method: "post",
    url: baseEndpointURL + "login"
  },
  addPost: {
    method: "post",
    url: baseEndpointURL + "addPost"
  },
  newPosts: {
    method: "get",
    url: baseEndpointURL + "posts"
  },
  likePost: {
    method: "post",
    url: baseEndpointURL + "like"
  },
  getUserInfos: {
    method: "get",
    url: baseEndpointURL + "user/{userEmail}"
  },
  getUserPosts: {
    method: "get",
    url: baseEndpointURL + "user/{userEmail}/posts"
  },
  getPost: {
    method: "get",
    url: baseEndpointURL + "post/{postId}"
  }
}

/**
 * classe regroupant les actions opérables par un utilisateur
 */
const User = {
  login: (googleUser) => {
    var profile = googleUser.getBasicProfile();
    
    const user = {
        email: profile.getEmail(),
        name: profile.getName(),
    };
    console.log(user);
    
    sessionStorage.setItem("user", JSON.stringify(user));
    
    return m.request({
        method: "post",
        url: "_ah/api/myApi/v1/postUser",
        params: user
    })
    .then(e => window.location = "/")
    .catch(e => {
        console.log(e);
        alert("Erreur lors de l'enregistrement de l'utilisateur");
    })
  },

  logout: () => {
    gapi.load('auth2', function() {
      var auth2 = gapi.auth2.getAuthInstance();
      auth2.signOut()
        .then(() => {
          alert("Déconnexion");
          sessionStorage.clear();
          window.location = "";
        })
        .catch(e => {
          alert("Erreur lors de la déconnexion");
          console.log(e);
        });
    });
  },

  /**
   * Fonction de like d'un post
   * @param {string} postId
   * @param  {string} userEmail
   */
  likePost: (postId) => {
    axios[EndpointURL.likePost.method](EndpointURL.likePost.url, {postId, userEmail: JSON.parse(sessionStorage.getItem("user")).email})
        .then(e => {
            View.updateLikeCount(postId.replace(/\_/g, " "))
        })
        .catch(error => {
            console.log(error);
            alert("Erreur lors du like")
        });
  },
}

function isUserConnected() {
    return Boolean(sessionStorage.getItem("user"));
}

function formatDate(date) {
    date = date.split(" ")[0].split("-");
    return [date[2], date[1], date[0]].join("/");
}

// // Si l'utilisateur n'est pas connecté
if (!isUserConnected() && window.location.pathname !== "/glogin.html") {
    console.log("oui1");
    window.location = "/glogin.html";
}