import { ref, onMounted } from "vue";

// ログイン・ユーザー情報を管理
const isLoggedIn = ref(false);
const loggedInUser = ref({
    id: "",
    name: "",
    email: "",
    password: "",
});

// クッキーを取得する
const getCookie = (name) => {
    if (typeof document !== "undefined") {
        const match = document.cookie.match("(^|;)\\s*" + name + "\\s*=\\s*([^;]+)");
        return match ? match.pop() : "";
    }
    return "";
};

// クッキーからセッション情報を取得する
const getSessionFromCookie = () => {
    const userId = getCookie("userId");
    const userName = getCookie("userName");
    const email = getCookie("email");
    const password = getCookie("password");

    if (userName) {
        loggedInUser.value.id = userId;
        loggedInUser.value.name = userName;
        loggedInUser.value.email = email;
        loggedInUser.value.password = password;
        isLoggedIn.value = true;
    } else {
        isLoggedIn.value = false;
        loggedInUser.value = {
            id: "",
            name: "",
            email: "",
            password: "",
        };
    }
};

// セッション情報を取得・返す
export const getSettion = () => {
    onMounted(() => {
        getSessionFromCookie();
    });
    return {
        isLoggedIn,
        loggedInUser,
        getSessionFromCookie,
    };
};