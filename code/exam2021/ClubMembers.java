package exam2021;

import java.util.ArrayList;

class MemberInfo {

    public MemberInfo(String name, int gradYear, boolean hasGoodStanding) {

    }

    public int getGradYear() {
        return -1;
    }

    public boolean inGoodStanding() {
        return false;
    }
}

public class ClubMembers {

    private ArrayList<MemberInfo> memberList;

    public void addMembers(String[] names, int gradYear) {
        for (String name : names) {
            memberList.add(new MemberInfo(name, gradYear, true));
        }
    }

    public ArrayList<MemberInfo> removeMembers(int year) {
        ArrayList<MemberInfo> good = new ArrayList<>();
        for (int i = memberList.size() - 1; i >= 0; i--) {
            var info = memberList.get(i);
            if (info.inGoodStanding() && info.getGradYear() == year) {
                good.add(info);
            }
            if (info.getGradYear() == year) {
                memberList.remove(i);
            }
        }
        return good;
    }
}
