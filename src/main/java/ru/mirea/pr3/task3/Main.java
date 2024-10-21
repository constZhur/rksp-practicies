package ru.mirea.pr3.task3;

import io.reactivex.Observable;

import static ru.mirea.pr3.task3.UserFriend.getFriends;

public class Main {
    public static void main(String[] args) {
        Integer[] userIdArray = {1, 2, 3, 4, 5, 7, 8};

        Observable<Integer> userIdStream = Observable.fromArray(userIdArray);

        Observable<UserFriend> userFriendStream = userIdStream
                .flatMap(userId -> getFriends(userId));

        userFriendStream.subscribe(userFriend -> {
            System.out.println("User: " + userFriend.getUserId() + ", Friend: "
                    + userFriend.getFriendId());
        });
    }
}
