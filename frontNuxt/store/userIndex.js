

export const userInfo = () => ({
    user_id: null,
    email: null,
    user_name: null,
});

export const mutations = {
    setUser(userInfo, user, email, user_name) {
        userInfo.user = user;
        userInfo.email = email;
        userInfo.user_name = user_name;
    }
};

