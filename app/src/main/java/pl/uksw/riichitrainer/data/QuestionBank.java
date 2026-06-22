package pl.uksw.riichitrainer.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import pl.uksw.riichitrainer.model.Question;

public class QuestionBank {
    public static final String MODE_DISCARD = "discard";
    public static final String MODE_YAKU = "yaku";
    public static final String MODE_READING = "reading";
    public static final String MODE_MIXED = "mixed";
    public static final int QUESTIONS_PER_GAME = 8; //default for this game but maybe later this can be moved to settings as preference or extended to longer games?

    public static ArrayList<Question> getQuestions(String mode, int maxDifficultyLevel, long seed) {
        ArrayList<Question> all = buildAllQuestions();
        ArrayList<Question> result = new ArrayList<Question>();

        for (int i = 0; i < all.size(); i++) {
            Question q = all.get(i);
            boolean modeMatches = MODE_MIXED.equals(mode) || q.getMode().equals(mode);
            boolean difficultyMatches = q.getDifficultyLevel() <= maxDifficultyLevel;

            if (modeMatches && difficultyMatches) {
                result.add(q);
            }
        }

        if (result.size() == 0) {
            return all;
        }

        Collections.shuffle(result, new Random(seed)); //the questions should be shuffled and the seed must be known to be able to reproduce questions after orintation change

        if (result.size() > QUESTIONS_PER_GAME) {
            return new ArrayList<Question>(result.subList(0, QUESTIONS_PER_GAME));
        }

        return result;
    }

    public static String getModeLabel(String mode) {
        if (MODE_DISCARD.equals(mode)) return "Discard decision";
        if (MODE_YAKU.equals(mode)) return "Yaku recognition";
        if (MODE_READING.equals(mode)) return "Opponent reading";
        return "Mixed quiz";
    }

    private static ArrayList<Question> buildAllQuestions() {
        ArrayList<Question> list = new ArrayList<Question>();

        addDiscardQuestions(list);
        addYakuQuestions(list);
        addReadingQuestions(list);

        return list;
    }

    private static void addDiscardQuestions(ArrayList<Question> list) {
        list.add(new Question(
                "d01", MODE_DISCARD, "Keep the hand flexible", 1,
                "East 1. You are dealer. No calls. What should you discard?",
                "2m 3m 4m 6m 4p 5p 6p 3s 4s 5s 7s 8s 9s E",
                "Your discards: 9m N", "Dora: 5p",
                new String[]{"E", "6m", "9s", "4p"}, 0,
                "East is isolated and does not improve the main structure. Discarding it keeps three completed sequences and useful flexible shapes.",
                "Pinfu / hand efficiency"));

        list.add(new Question(
                "d02", MODE_DISCARD, "All Simples direction", 1,
                "You have a mostly simple-tile hand. Which discard best supports All Simples?",
                "2m 3m 4m 1m 3p 4p 5p 6p 7p 8p 2s 3s 4s 6s",
                "Your discards: E Wh", "Dora: 6p",
                new String[]{"1m", "6s", "2m", "8p"}, 0,
                "The 1m is a terminal and does not fit All Simples. The other tiles either form completed sequences or remain useful simple-tile material.",
                "All Simples"));

        list.add(new Question(
                "d03", MODE_DISCARD, "Keep the dora block", 1,
                "East 3. Dora is 5s. You want speed and value. What should you discard?",
                "2m 3m 4m 3p 4p 5p 6p 4s 5s 6s 7s 8s 9s S",
                "Your discards: 1m 9p", "Dora: 5s",
                new String[]{"S", "4s", "9s", "2m"}, 0,
                "South is isolated. The souzu block contains dora and connected sequences, so it should be preserved.",
                "Dora / All Simples"));

        list.add(new Question(
                "d04", MODE_DISCARD, "Discard isolated terminal", 1,
                "You have several good blocks and one weak isolated terminal. What should you discard?",
                "3m 4m 5m 6m 7m 8m 2p 2p 4p 5p 6p 1s 7s 8s",
                "Your discards: E N", "Dora: 3p",
                new String[]{"1s", "2p", "4p", "7s"}, 0,
                "The 1s is isolated and weak. The 7s-8s shape can still accept 6s or 9s, while 2p is your pair.",
                "Hand efficiency"));

        list.add(new Question(
                "d05", MODE_DISCARD, "Keep value honor pair", 1,
                "South 1. You already have a pair of red dragons. Which discard is best?",
                "2m 3m 4m 9m 4p 5p 6p 6s 7s 8s R R 1p 5s",
                "Your discards: W 9p", "Dora: 5s",
                new String[]{"9m", "R", "5s", "4p"}, 0,
                "The pair of red dragons can become a value honor triplet, giving the hand a yaku. The isolated 9m is much less useful.",
                "Value honor / speed"));

        list.add(new Question(
                "d06", MODE_DISCARD, "Seven Pairs possibility", 1,
                "You have many pairs. Which isolated tile is the weakest discard?",
                "2m 2m 5m 5m 8m 8m 3p 3p 7p 7p E E 4s 9s",
                "Your discards: 1m 9p", "Dora: 4s",
                new String[]{"9s", "4s", "E", "7p"}, 0,
                "With many pairs, Seven Pairs is a natural direction. Between the two singletons, 4s is much more flexible than isolated 9s.",
                "Seven Pairs"));

        list.add(new Question(
                "d07", MODE_DISCARD, "Half Flush direction", 1,
                "Your hand is almost entirely pinzu with honors. What should you discard?",
                "2p 3p 4p 4p 5p 6p 7p 8p 9p E E R R 5m",
                "Your discards: 1s 9s", "Dora: 8p",
                new String[]{"5m", "E", "R", "7p"}, 0,
                "The 5m is the only off-suit tile. Discarding it keeps the hand moving toward Half Flush with value honor support.",
                "Half Flush"));

        list.add(new Question(
                "d08", MODE_DISCARD, "Pure Straight shape", 1,
                "You have 123, 456 and 789 in the same suit. Which discard keeps the cleanest structure?",
                "1m 2m 3m 4m 5m 6m 7m 8m 9m 2p 2p 6s 8s E",
                "Your discards: S W", "Dora: 2p",
                new String[]{"E", "2p", "7m", "6s"}, 0,
                "East is isolated. The manzu structure already forms a Pure Straight shape, and 2p can serve as the pair.",
                "Pure Straight"));

        list.add(new Question(
                "d09", MODE_DISCARD, "Basic defense: genbutsu", 1,
                "An opponent declared Riichi. You are far from tenpai. Which tile is safest?",
                "1m 3m 5m 8m 2p 4p 7p 9p 1s 3s 6s 8s Wh G",
                "Riichi player's discards: E 9m Wh 2s 5p", "Dora: 7p",
                new String[]{"Wh", "7p", "5m", "8s"}, 0,
                "White dragon is already in the Riichi player's river, so it is genbutsu against that player. Pushing dora 7p is risky here.",
                "Defense / genbutsu"));

        list.add(new Question(
                "d10", MODE_DISCARD, "Three-player Kita action", 1,
                "Three-player game. You draw North and it is used as Kita bonus in this ruleset. What is usually the best action?",
                "1m 9m 2p 3p 4p 5p 6p 7p 3s 4s 5s 8s 8s N",
                "Your discards: 9p 1s", "Dora: 5p",
                new String[]{"Declare Kita", "Discard 8s", "Discard 1m", "Discard 5p"}, 0,
                "In many three-player Riichi variants, North can be declared as Kita for bonus value and a replacement draw. If it is not needed for the hand, declaring it is usually correct.",
                "Kita / sanma"));

        list.add(new Question(
                "d11", MODE_DISCARD, "Do not break two-sided shapes", 2,
                "You have several good two-sided shapes and one isolated terminal. What should you discard?",
                "2m 3m 6m 7m 3p 4p 5p 5p 6p 7p 2s 3s 4s 9s",
                "Your discards: E R", "Dora: 6m",
                new String[]{"9s", "2m", "7m", "5p"}, 0,
                "The 9s is isolated. The 2m-3m and 6m-7m shapes are useful two-sided shapes, and the pinzu and souzu blocks are already strong.",
                "Hand efficiency"));

        list.add(new Question(
                "d12", MODE_DISCARD, "Dora acceptance", 2,
                "Dora is 5p. Which discard keeps the best chance to use it?",
                "2m 3m 4m 6m 7m 8m 3p 4p 6p 2s 3s 4s E E",
                "Your discards: 9m 1s", "Dora: 5p",
                new String[]{"E", "4p", "6p", "2s"}, 0,
                "The 3p-4p-6p shape can accept 5p, the dora. Discarding one East keeps speed while preserving the valuable pinzu shape.",
                "Dora awareness"));

        list.add(new Question(
                "d13", MODE_DISCARD, "Headless hand", 2,
                "Your hand has good sequence blocks but no clear pair. Which discard is most reasonable?",
                "2m 3m 4m 4m 5m 6m 2p 3p 4p 6s 7s 8s 5p 9p",
                "Your discards: E S", "Dora: 5p",
                new String[]{"9p", "5p", "2m", "7s"}, 0,
                "The hand needs a pair. The 5p is useful because it can become the pair and is also dora. The isolated 9p is much weaker.",
                "Pinfu / pair search"));

        list.add(new Question(
                "d14", MODE_DISCARD, "Mixed Triple Sequence possibility", 2,
                "You have 3-4-5 shapes in all three suits. What should you discard?",
                "3m 4m 5m 3p 4p 5p 3s 4s 5s 7s 8s 9s E 1p",
                "Your discards: N Wh", "Dora: 4m",
                new String[]{"E", "5s", "3m", "7s"}, 0,
                "East is isolated. Keeping 3-4-5 in all three suits preserves the possibility of Mixed Triple Sequence.",
                "Mixed Triple Sequence"));

        list.add(new Question(
                "d15", MODE_DISCARD, "Pure Straight over random shape", 2,
                "Your manzu shape already suggests 1-9 structure. Which discard keeps that plan clean?",
                "1m 2m 3m 4m 5m 6m 7m 8m 9m 2p 2p 5s 7s E",
                "Your discards: R 9p", "Dora: 2p",
                new String[]{"E", "2p", "5s", "8m"}, 0,
                "East is isolated. The manzu tiles form 123, 456 and 789, so the Pure Straight structure should not be broken.",
                "Pure Straight"));

        list.add(new Question(
                "d16", MODE_DISCARD, "All Triplets direction", 2,
                "Your hand already contains several triplet blocks. Which discard fits the hand direction?",
                "2m 2m 2m 3m 4m 5p 5p 8s 8s 8s R R R N",
                "Your discards: 6p 9m", "Dora: N",
                new String[]{"3m", "5p", "8s", "R"}, 0,
                "The 3m-4m shape is the only sequence-like part of the hand. Discarding 3m moves the hand toward All Triplets while keeping pairs and triplets.",
                "All Triplets"));

        list.add(new Question(
                "d17", MODE_DISCARD, "Half Outside Hand direction", 2,
                "Your hand has terminals in most blocks. What should you discard?",
                "1m 2m 3m 7m 8m 9m 1p 2p 3p 7s 8s Wh Wh 5s",
                "Your discards: 4p 6p", "Dora: Wh",
                new String[]{"5s", "Wh", "1m", "8s"}, 0,
                "The 5s is a simple tile that does not fit Half Outside Hand. Keeping terminal-based sequences and the dragon pair preserves the yaku direction.",
                "Half Outside Hand"));

        list.add(new Question(
                "d18", MODE_DISCARD, "Keep Red Dora", 2,
                "You have a red five in pinzu. Which discard keeps value and efficiency?",
                "2m 3m 4m 9m 3p 4p 5p* 6p 7p 8p 2s 3s 4s E",
                "Your discards: S Wh", "Dora: 6p",
                new String[]{"E", "5p*", "6p", "2s"}, 0,
                "The red five is valuable and also works with 3p-4p. East is isolated, so it is the cleanest discard.",
                "Red Dora / efficiency"));

        list.add(new Question(
                "d19", MODE_DISCARD, "Value honor or All Simples", 2,
                "You have an isolated honor and an isolated terminal. Which discard is better for fast development?",
                "2m 3m 4m 5m 6m 7m 2p 3p 4p 5s 6s 7s Wh 1p",
                "Your discards: N 9m", "Dora: 5m",
                new String[]{"Wh", "1p", "5m", "7s"}, 0,
                "White dragon is isolated and not paired. Keeping 1p at least leaves some chance to connect with 2p or 3p, while Wh only helps if another Wh appears.",
                "Speed / tile efficiency"));

        list.add(new Question(
                "d20", MODE_DISCARD, "Sanma dora indicator rule", 2,
                "Three-player game. Dora indicator is 1m, so 9m is dora. What should you discard?",
                "1m 9m 2p 3p 4p 5p 6p 7p 3s 4s 5s E E 9p",
                "Your discards: 1s 9s", "Dora indicator: 1m -> Dora: 9m",
                new String[]{"9p", "9m", "E", "5p"}, 0,
                "In this three-player rule context, 1m indicates 9m as dora. The isolated 9p is much easier to discard than the dora 9m or the East pair.",
                "Sanma / dora indicator"));

        list.add(new Question(
                "d21", MODE_DISCARD, "Five-block efficiency", 2,
                "You have more useful blocks than needed. Which tile is the weakest cut?",
                "2m 3m 4m 5m 6m 7m 3p 4p 5p 6p 7p 8p 4s 8s",
                "Your discards: E G", "Dora: 4s",
                new String[]{"8s", "4s", "2m", "6p"}, 0,
                "The 8s is isolated. The 4s is dora and can still connect, while the other tiles belong to completed or strong sequence blocks.",
                "Five-block theory"));

        list.add(new Question(
                "d22", MODE_DISCARD, "Keep dragon pair", 2,
                "You have a green dragon pair and several sequence blocks. Which discard is best?",
                "2m 3m 4m 5m 6m 7m 2p 3p 4p 5s 6s G G 9p",
                "Your discards: W 1s", "Dora: 7s",
                new String[]{"9p", "G", "5s", "2m"}, 0,
                "The green dragon pair can become a value honor triplet. The isolated 9p is less useful than the pair or the 5s-6s shape.",
                "Value honor / speed"));

        list.add(new Question(
                "d23", MODE_DISCARD, "Thirteen Orphans direction", 3,
                "You have many terminals and honors. Which discard keeps the rare hand direction?",
                "1m 9m 1p 9p 1s 9s E S W N Wh G R 5p",
                "Your discards: 2m 6s", "Dora: R",
                new String[]{"5p", "R", "1m", "N"}, 0,
                "The 5p is the only tile that does not belong to Thirteen Orphans. All terminals, winds and dragons should be kept.",
                "Thirteen Orphans"));

        list.add(new Question(
                "d24", MODE_DISCARD, "All Green possibility", 3,
                "You have a rare All Green shape developing. Which tile clearly does not belong?",
                "2s 3s 4s 2s 3s 4s 6s 6s 8s 8s G G 2s 5m",
                "Your discards: 1m 9p", "Dora: G",
                new String[]{"5m", "G", "2s", "8s"}, 0,
                "All Green uses only green souzu tiles and green dragons. The 5m is completely outside that structure.",
                "All Green"));

        list.add(new Question(
                "d25", MODE_DISCARD, "Big Three Dragons possibility", 3,
                "You have pairs of all three dragons. Which discard keeps the rare value direction?",
                "R R G G Wh Wh 2m 2m 7p 7p 3s 4s 5s 9m",
                "Your discards: 1p 9s", "Dora: Wh",
                new String[]{"9m", "R", "Wh", "7p"}, 0,
                "The dragon pairs are the core of a possible Big Three Dragons or Little Three Dragons hand. The isolated 9m is the least important tile.",
                "Big Three Dragons / Little Three Dragons"));

        list.add(new Question(
                "d26", MODE_DISCARD, "Three Concealed Triplets shape", 3,
                "You already have several concealed triplets. Which discard preserves the strongest plan?",
                "2m 2m 2m 5p 5p 5p 8s 8s 8s 3m 4m 5m N 9p",
                "Your discards: E Wh", "Dora: 5p",
                new String[]{"9p", "2m", "5p", "8s"}, 0,
                "The triplets should not be broken. The isolated 9p is expendable while the hand can still develop around concealed triplets and value.",
                "Three Concealed Triplets"));

        list.add(new Question(
                "d27", MODE_DISCARD, "Ippatsu turn defense", 3,
                "An opponent declared Riichi on the previous discard. You are not tenpai. What should you discard?",
                "2m 4m 6m 8m 2p 5p 7p 1s 4s 6s Wh G R 3p",
                "Riichi player's discards: 9m 1p 3p, then Riichi", "Dora: 5p",
                new String[]{"3p", "5p", "R", "8m"}, 0,
                "During the Ippatsu turn, avoid unnecessary risk. The 3p is genbutsu because the Riichi player has already discarded it.",
                "Ippatsu / defense"));

        list.add(new Question(
                "d28", MODE_DISCARD, "Suji is not genbutsu", 3,
                "Opponent declared Riichi. Which discard follows the safest beginner principle?",
                "1m 4m 7m 2p 3p 5p 8p 2s 5s 8s E S W 9s",
                "Riichi player's discards: 6p 9s 1m, then Riichi", "Dora: 5p",
                new String[]{"9s", "3p", "5p", "E"}, 0,
                "Even if 3p may look related to suji from 6p, it is not as safe as genbutsu. The 9s is already in the Riichi player's river.",
                "Defense / genbutsu over suji"));

        list.add(new Question(
                "d29", MODE_DISCARD, "Dora danger while folding", 3,
                "You are folding against Riichi. Which discard avoids pushing the most dangerous value tile?",
                "2m 3m 6m 7m 9m 2p 4p 5p 7p 3s 5s 7s G Wh",
                "Riichi player's discards: 1m 9m Wh 4s 8p, then Riichi", "Dora: 5p",
                new String[]{"Wh", "5p", "7p", "G"}, 0,
                "White dragon is genbutsu. The 5p is dora and should not be pushed while folding unless there is a very strong reason.",
                "Defense / dora danger"));

        list.add(new Question(
                "d30", MODE_DISCARD, "Full Flush commitment", 3,
                "Your hand is very close to a Full Flush in souzu. Which discard commits to the strongest value direction?",
                "2s 3s 4s 4s 5s 6s 6s 7s 8s 7s 8s 9s 5p 9m",
                "Your discards: 1m 2p 6p", "Dora: 6s",
                new String[]{"9m", "6s", "5s", "7s"}, 0,
                "The hand is almost entirely souzu. Discarding the off-suit 9m keeps the Full Flush direction and preserves the dora 6s.",
                "Full Flush"));

        list.add(new Question(
                "d31", MODE_DISCARD, "Full Outside Hand vs speed", 3,
                "Your hand has no honors and every block can include a terminal. Which discard keeps Full Outside Hand alive?",
                "1m 2m 3m 7m 8m 9m 1p 2p 3p 7p 8p 9p 5s 9s",
                "Your discards: E S Wh", "Dora: 9p",
                new String[]{"5s", "9s", "1m", "8p"}, 0,
                "The 5s is the only simple tile that cannot fit Full Outside Hand. Keeping terminal-based sequences preserves the rare yaku direction.",
                "Full Outside Hand"));

        list.add(new Question(
                "d32", MODE_DISCARD, "Sanma: protect 9m dora", 3,
                "Three-player game. Opponent declared Riichi. Dora indicator is 1m, so 9m is dora. Which discard is safest?",
                "1m 9m 2p 4p 5p 7p 8p 3s 5s 6s 8s Wh G R",
                "Riichi player's discards: 1p 9p Wh 3s, then Riichi", "Dora indicator: 1m -> Dora: 9m",
                new String[]{"Wh", "9m", "5p", "R"}, 0,
                "White dragon is genbutsu. The 9m is dora in this sanma indicator rule, so discarding it against Riichi is especially risky.",
                "Sanma / defense / dora"));

        list.add(new Question(
                "d33", MODE_DISCARD, "Red Dora and All Simples", 3,
                "You have Red Dora and a possible All Simples hand. Which discard best keeps value?",
                "2m 3m 4m 6m 7m 8m 3p 4p 5p* 6p 7p 8p 1s E",
                "Your discards: Wh N", "Dora: 7p",
                new String[]{"E", "5p*", "7p", "4p"}, 0,
                "East is isolated and prevents All Simples. The red five and dora-related pinzu shape give the hand value and should be kept.",
                "Red Dora / All Simples"));

        list.add(new Question(
                "d34", MODE_DISCARD, "Rare yakuman temptation", 3,
                "You have a very unusual honor-heavy hand. Which discard keeps All Honors possible?",
                "E E S S W W N N Wh Wh G G R 5p",
                "Your discards: 2m 8s", "Dora: R",
                new String[]{"5p", "E", "Wh", "R"}, 0,
                "All Honors requires only wind and dragon tiles. The 5p is the only numbered tile and should be discarded if you commit to that rare direction.",
                "All Honors"));
    }

    private static void addYakuQuestions(ArrayList<Question> list) {
        list.add(new Question(
                "y01", MODE_YAKU, "All Simples direction", 1,
                "Your hand has no terminals or honors. Which yaku is the most natural direction?",
                "2m 3m 4m 3p 4p 5p 6p 7p 8p 2s 3s 4s 6s",
                "Your discards: E N", "Dora: 6s",
                new String[]{"All Simples", "Half Flush", "Half Outside Hand", "All Terminals and Honors"}, 0,
                "All Simples uses only numbered tiles from 2 to 8. This hand already fits that direction and can also combine with Riichi, Pinfu or Dora.",
                "All Simples"));

        list.add(new Question(
                "y02", MODE_YAKU, "Pinfu structure", 1,
                "Closed hand. You have four sequence-based blocks and a non-value pair. Which yaku should you look for?",
                "2m 3m 4m 3p 4p 5p 6p 7p 8p 3s 4s 5s 7s 7s",
                "Your discards: E R", "Dora: 4p",
                new String[]{"Pinfu", "All Triplets", "Seven Pairs", "All Honors"}, 0,
                "Pinfu is built from sequences, a non-value pair, and a two-sided wait. This hand is already close to that structure.",
                "Pinfu"));

        list.add(new Question(
                "y03", MODE_YAKU, "Value Honor pair", 1,
                "You have a pair of red dragons. Which yaku can this hand naturally build toward?",
                "2m 3m 4m 4p 5p 6p 6s 7s 8s R R 1p 3p",
                "Your discards: W 9m", "Dora: 5p",
                new String[]{"Value Honor", "Pinfu", "All Simples", "Full Flush"}, 0,
                "A triplet of dragons gives Value Honor. The pair of red dragons is therefore valuable and can give the hand a yaku if completed.",
                "Value Honor"));

        list.add(new Question(
                "y04", MODE_YAKU, "Seven Pairs shape", 1,
                "You have six pairs and one extra tile. Which yaku is the obvious direction?",
                "2m 2m 5m 5m 8m 8m 3p 3p 7p 7p E E 4s",
                "Your discards: 1m 9p", "Dora: 4s",
                new String[]{"Seven Pairs", "Pinfu", "Pure Straight", "Full Outside Hand"}, 0,
                "Six pairs strongly suggest Seven Pairs. The hand needs one more pair to complete that yaku.",
                "Seven Pairs"));

        list.add(new Question(
                "y05", MODE_YAKU, "All Triplets direction", 1,
                "Your hand already contains triplets and pairs. Which yaku direction fits best?",
                "2m 2m 2m 5p 5p 5p 8s 8s W W W N N",
                "Your discards: 3m 7p", "Dora: N",
                new String[]{"All Triplets", "Pinfu", "Pure Double Sequence", "All Simples"}, 0,
                "The hand is made from triplet blocks and pairs, so All Triplets is the natural direction.",
                "All Triplets"));

        list.add(new Question(
                "y06", MODE_YAKU, "Half Flush direction", 1,
                "Your hand is almost one suit plus honors. Which yaku direction is the most obvious?",
                "2p 3p 4p 4p 5p 6p 7p 8p 9p E E S S",
                "Your discards: 1m 9s 3m", "Dora: 8p",
                new String[]{"Half Flush", "All Simples", "Pinfu only", "Half Outside Hand"}, 0,
                "The hand uses only pinzu and honor tiles. That points toward Half Flush.",
                "Half Flush"));

        list.add(new Question(
                "y07", MODE_YAKU, "Pure Straight", 1,
                "You have 123, 456 and 789 in the same suit. Which yaku should you recognize?",
                "1m 2m 3m 4m 5m 6m 7m 8m 9m 2p 2p E E",
                "Your discards: 7s 9p", "Dora: 2p",
                new String[]{"Pure Straight", "Mixed Triple Sequence", "Seven Pairs", "All Green"}, 0,
                "Pure Straight is made from 123, 456 and 789 in one suit.",
                "Pure Straight"));

        list.add(new Question(
                "y08", MODE_YAKU, "Mixed Triple Sequence", 1,
                "You have the same sequence in all three suits. Which yaku is suggested?",
                "2m 3m 4m 2p 3p 4p 2s 3s 4s 7m 8m 9m E",
                "Your discards: N 9p", "Dora: 4m",
                new String[]{"Mixed Triple Sequence", "Value Honor", "Seven Pairs", "All Terminals and Honors"}, 0,
                "The same 2-3-4 sequence appears in manzu, pinzu and souzu. This suggests Mixed Triple Sequence.",
                "Mixed Triple Sequence"));

        list.add(new Question(
                "y09", MODE_YAKU, "Half Outside Hand", 1,
                "Each block seems to contain a terminal or honor. Which yaku direction fits?",
                "1m 2m 3m 7m 8m 9m 1p 2p 3p Wh Wh 7s 8s",
                "Your discards: 4p 5p", "Dora: Wh",
                new String[]{"Half Outside Hand", "All Simples", "Pinfu only", "All Green"}, 0,
                "Half Outside Hand uses terminals or honors in every group. This hand already has terminal-based sequences and a dragon pair.",
                "Half Outside Hand"));

        list.add(new Question(
                "y10", MODE_YAKU, "Dora is not a yaku", 1,
                "Closed hand. You have dora, but no clear yaku yet. What is the most important rule?",
                "1m 2m 3m 4p 5p 6p 7s 8s 9s 2m 5p 8s Wh",
                "Your discards: E N", "Dora: 5p",
                new String[]{"Dora gives value, but it is not a yaku", "Dora alone always allows winning", "Dora replaces Riichi", "Dora makes Pinfu open"}, 0,
                "Dora increases the hand value, but it does not give a yaku by itself. You still need a valid yaku such as Riichi, Pinfu, All Simples or Value Honor.",
                "Dora rule"));

        list.add(new Question(
                "y11", MODE_YAKU, "Riichi as a yaku", 1,
                "Closed hand. You are in tenpai and have no open calls. Which yaku can you declare?",
                "2m 3m 4m 4p 5p 6p 6s 7s 8s 3m 4m 5m R",
                "Your discards: 1p 9s", "Dora: R",
                new String[]{"Riichi", "All Triplets", "Half Flush", "Robbing a Kan"}, 0,
                "Riichi is available only for a closed hand in tenpai. It is often the simplest way to secure a yaku.",
                "Riichi"));

        list.add(new Question(
                "y12", MODE_YAKU, "Open hand limitation", 1,
                "You called chi earlier. Which yaku is no longer available because the hand is open?",
                "2m 3m 4m 4p 5p 6p 6s 7s 8s E E 5m 6m",
                "Open call: 2m3m4m. Your discards: 9p N", "Dora: 5m",
                new String[]{"Riichi", "Value Honor", "All Triplets", "Half Flush"}, 0,
                "Riichi requires a closed hand. Once you call, Riichi is no longer available.",
                "Open hand rules"));

        list.add(new Question(
                "y13", MODE_YAKU, "Open Pure Straight value", 2,
                "You called chi and completed 123, 456 and 789 in manzu. What is true?",
                "1m 2m 3m 4m 5m 6m 7m 8m 9m 5p 5p E E",
                "Open call: 1m2m3m. Your discards: 9s Wh", "Dora: 5p",
                new String[]{"Pure Straight is still valid, but reduced when open", "Pure Straight disappears when open", "It becomes Full Flush", "It becomes Pinfu"}, 0,
                "Pure Straight can be open, but it is worth less when open.",
                "Pure Straight"));

        list.add(new Question(
                "y14", MODE_YAKU, "Closed Half Flush value", 2,
                "Closed hand. You have only souzu and honors. Which statement is correct?",
                "2s 3s 4s 4s 5s 6s 6s 7s 8s E E G G",
                "Your discards: 1m 9m 2p", "Dora: G",
                new String[]{"Closed Half Flush is worth more than open Half Flush", "Half Flush is impossible with honors", "Half Flush requires all three suits", "Half Flush is always yakuman"}, 0,
                "Half Flush uses one suit plus honors. It is more valuable closed than open.",
                "Half Flush"));

        list.add(new Question(
                "y15", MODE_YAKU, "Strategic value choice", 2,
                "You considered Half Flush, but you already have two dora in a fast simple hand. What is the better practical direction?",
                "2m 3m 4m 4p 5p* 6p 6p 7p 8p 3s 4s 5s 7s",
                "Your discards: E Wh 9m", "Dora: 7p",
                new String[]{"Fast All Simples / Pinfu with Dora", "Force Half Flush", "Force Thirteen Orphans", "Force All Honors"}, 0,
                "The hand is already fast and valuable because of Red Dora and normal Dora. Forcing Half Flush would slow down a hand that is already promising.",
                "Efficiency / Dora"));

        list.add(new Question(
                "y16", MODE_YAKU, "Blocked Value Honor", 2,
                "You have one green dragon, but two green dragons are already visible in opponents' discards. What does that mean?",
                "2m 3m 4m 4p 5p 6p 6s 7s 8s G 2p 3p 4p",
                "Visible discards: G G by opponents", "Dora: 5s",
                new String[]{"Value Honor with green dragons is no longer possible", "Green dragon is now dora", "Pinfu is impossible", "All Simples is impossible"}, 0,
                "A dragon triplet requires three copies. If two green dragons are already visible outside your hand and you hold only one, you cannot complete that triplet.",
                "Reading visible tiles"));

        list.add(new Question(
                "y17", MODE_YAKU, "Choosing between Seven Pairs and sequences", 2,
                "You have five pairs, but also several connected shapes. What is usually the more flexible plan?",
                "2m 2m 3m 4m 5m 5m 6p 6p 7p 8p 2s 2s 3s 4s",
                "Your discards: E N", "Dora: 6p",
                new String[]{"Keep both Seven Pairs and sequence options for now", "Immediately discard all sequences", "Force All Honors", "Discard every pair"}, 0,
                "With five pairs and connected shapes, the hand can still go toward Seven Pairs or a normal four-groups-and-a-pair hand. Keeping flexibility is best.",
                "Hand building"));

        list.add(new Question(
                "y18", MODE_YAKU, "Full Flush vs Half Flush", 2,
                "Your hand is one suit plus honors. What would increase the value but usually reduce speed?",
                "2p 3p 4p 4p 5p 6p 7p 8p 9p E E R R",
                "Your discards: 1m 9s", "Dora: 8p",
                new String[]{"Removing honors and aiming for Full Flush", "Adding another suit", "Calling random terminals", "Breaking the pinzu blocks"}, 0,
                "Full Flush uses only one numbered suit and no honors. It is more valuable than Half Flush, but often harder and slower.",
                "Full Flush / Half Flush"));

        list.add(new Question(
                "y19", MODE_YAKU, "Simple han count", 2,
                "Closed winning hand. You declared Riichi. The hand has Pinfu, All Simples and one Dora. How many han before Ura Dora?",
                "2m 3m 4m 3p 4p 5p 6p 7p 8p 3s 4s 5s 7s 7s",
                "Win: Ron after Riichi. No Ippatsu.", "Dora: 4p",
                new String[]{"4 han", "2 han", "6 han", "1 han"}, 0,
                "Riichi is 1 han, Pinfu is 1 han, All Simples is 1 han and Dora is 1 han. Total: 4 han.",
                "Han counting"));

        list.add(new Question(
                "y20", MODE_YAKU, "Seven Pairs with Riichi", 2,
                "Closed winning hand. You declared Riichi. You win Seven Pairs with one Dora. How many han before Ura Dora?",
                "2m 2m 5m 5m 8m 8m 3p 3p 7p 7p E E 4s 4s",
                "Win: Ron after Riichi. No Ippatsu.", "Dora: 4s",
                new String[]{"4 han", "2 han", "3 han", "6 han"}, 0,
                "Seven Pairs is 2 han, Riichi is 1 han and Dora is 1 han. Total: 4 han.",
                "Seven Pairs / han counting"));

        list.add(new Question(
                "y21", MODE_YAKU, "Red Dora meaning", 2,
                "The hand contains 5p*. What does the star mean in this app notation?",
                "2m 3m 4m 3p 4p 5p* 6p 7p 8p 3s 4s 5s E",
                "Your discards: 9m Wh", "Dora: 7p",
                new String[]{"Red Dora / Aka Dora", "Ura Dora already revealed", "White dragon", "A dead tile"}, 0,
                "The star marks a red five, also called Red Dora or Aka Dora. It gives bonus value but is not a yaku by itself.",
                "Red Dora"));

        list.add(new Question(
                "y22", MODE_YAKU, "Kita in three-player Riichi", 2,
                "Three-player game. You declared two Kita and won with Riichi and All Simples. How should Kita be treated?",
                "2p 3p 4p 4p 5p 6p 6s 7s 8s 3s 4s 5s 7p 7p",
                "Win: Riichi. Kita declared: 2", "Dora: none",
                new String[]{"Kita adds bonus value, but it is not a yaku by itself", "Kita replaces Riichi", "Kita makes the hand closed again after calls", "Kita removes All Simples"}, 0,
                "In many sanma rulesets, Kita works as bonus value. It increases the hand value but does not replace the need for a yaku.",
                "Kita / sanma"));

        list.add(new Question(
                "y23", MODE_YAKU, "Ura Dora condition", 2,
                "When can Ura Dora be checked?",
                "2m 3m 4m 4p 5p 6p 6s 7s 8s 3m 4m 5m R R",
                "Win: Ron", "Dora: 5s",
                new String[]{"Only after winning with Riichi", "After every open hand", "Whenever you have Red Dora", "Only in three-player games"}, 0,
                "Ura Dora is checked only when the winning player had declared Riichi.",
                "Ura Dora"));

        list.add(new Question(
                "y24", MODE_YAKU, "Dragon dora indicator", 2,
                "Dora indicator is Wh. Under standard dragon order Wh -> G -> R -> Wh, what is the dora?",
                "2m 3m 4m 4p 5p 6p 6s 7s 8s Wh G R E",
                "Your discards: 1m 9p", "Dora indicator: Wh",
                new String[]{"G", "Wh", "R", "E"}, 0,
                "For dragons, the dora order cycles White, Green, Red, then back to White. If Wh is the indicator, G is dora.",
                "Dora indicator"));

        list.add(new Question(
                "y25", MODE_YAKU, "Counted Yakuman threshold", 3,
                "A hand reaches 13 or more han without being a named yakuman. What can it become in many rulesets?",
                "2m 3m 4m 3p 4p 5p 6p 7p 8p 3s 4s 5s 7s 7s",
                "Example: Riichi, Pinfu, All Simples, many Dora and Ura Dora", "Dora: multiple",
                new String[]{"Counted Yakuman", "Seven Pairs", "Half Flush", "Robbing a Kan"}, 0,
                "In many rulesets, 13 or more han can be treated as Counted Yakuman. This depends on the ruleset, but it is a common scoring concept.",
                "Counted Yakuman"));

        list.add(new Question(
                "y26", MODE_YAKU, "Little Three Dragons value", 3,
                "You have triplets of two dragons and a pair of the third dragon. Which yaku structure is present?",
                "R R R G G G Wh Wh 2m 3m 4m 7p 8p 9p",
                "Closed hand. Win: Ron.", "Dora: 4m",
                new String[]{"Little Three Dragons plus two Value Honors", "Big Three Dragons", "All Simples", "Pinfu"}, 0,
                "Little Three Dragons requires two dragon triplets and a pair of the third dragon. The two dragon triplets also each count as Value Honor.",
                "Little Three Dragons"));

        list.add(new Question(
                "y27", MODE_YAKU, "All Terminals and Honors with Seven Pairs", 3,
                "Closed winning hand. Which combination best describes this hand?",
                "1m 1m 9m 9m 1p 1p 9p 9p E E Wh Wh R R",
                "Win: Ron. No Riichi.", "Dora: none",
                new String[]{"All Terminals and Honors + Seven Pairs", "All Simples + Pinfu", "Full Flush", "All Green"}, 0,
                "The hand consists only of terminals and honors and is also a Seven Pairs hand. This gives All Terminals and Honors plus Seven Pairs.",
                "All Terminals and Honors"));

        list.add(new Question(
                "y28", MODE_YAKU, "Twice Pure Double Sequence vs Seven Pairs", 3,
                "Closed winning hand. This shape can look like Seven Pairs, but what is the stronger yaku interpretation?",
                "2m 2m 3m 3m 4m 4m 2p 2p 3p 3p 4p 4p 5s 5s",
                "Win: Ron. No Riichi.", "Dora: none",
                new String[]{"Twice Pure Double Sequence", "All Triplets", "Half Flush", "Thirteen Orphans"}, 0,
                "The hand can be arranged as 234m twice and 234p twice, plus a pair. That is Twice Pure Double Sequence, which is stronger than treating it as Seven Pairs.",
                "Twice Pure Double Sequence"));

        list.add(new Question(
                "y29", MODE_YAKU, "Three Concealed Triplets condition", 3,
                "You have three concealed triplets and win by Ron on the pair. Which yaku can still count?",
                "2m 2m 2m 5p 5p 5p 8s 8s 8s 3m 4m 5m N N",
                "Win: Ron on N. No calls.", "Dora: 5p",
                new String[]{"Three Concealed Triplets", "Pinfu", "All Simples only", "Pure Straight"}, 0,
                "Winning by Ron on the pair does not open the triplets. The three triplets remain concealed, so Three Concealed Triplets can count.",
                "Three Concealed Triplets"));

        list.add(new Question(
                "y30", MODE_YAKU, "Four Concealed Triplets", 3,
                "Closed hand. You have four concealed triplets and win on the pair wait. What is the yaku class?",
                "2m 2m 2m 5p 5p 5p 8s 8s 8s R R R N N",
                "Win: Ron on N. No calls.", "Dora: none",
                new String[]{"Four Concealed Triplets", "All Simples", "Pure Straight", "Half Outside Hand"}, 0,
                "Four Concealed Triplets is a yakuman. In many rulesets, winning on the pair wait is the strongest version of this hand.",
                "Four Concealed Triplets"));

        list.add(new Question(
                "y31", MODE_YAKU, "Big Four Winds", 3,
                "Your hand contains triplets of all four winds. Which yakuman is this?",
                "E E E S S S W W W N N N 5p 5p",
                "Win: Ron. No other calls shown.", "Dora: 5p",
                new String[]{"Big Four Winds", "Little Three Dragons", "All Green", "Nine Gates"}, 0,
                "Triplets of all four winds form Big Four Winds, a yakuman.",
                "Big Four Winds"));

        list.add(new Question(
                "y32", MODE_YAKU, "All Green restriction", 3,
                "Which tile clearly does not belong to All Green?",
                "2s 3s 4s 2s 3s 4s 6s 6s 8s 8s G G 5s",
                "Your discards: 1m 9p", "Dora: G",
                new String[]{"5s", "G", "2s", "8s"}, 0,
                "All Green uses only green tiles: 2s, 3s, 4s, 6s, 8s and green dragons. The 5s is not part of that set.",
                "All Green"));

        list.add(new Question(
                "y33", MODE_YAKU, "Thirteen Orphans", 3,
                "You have one of each terminal and honor plus an extra terminal. Which yakuman direction is this?",
                "1m 9m 1p 9p 1s 9s E S W N Wh G R 1m",
                "Your discards: 5p 6s", "Dora: R",
                new String[]{"Thirteen Orphans", "All Simples", "Pure Straight", "All Triplets"}, 0,
                "Thirteen Orphans uses all thirteen terminal and honor types, plus one duplicate among them.",
                "Thirteen Orphans"));

        list.add(new Question(
                "y34", MODE_YAKU, "Sanma dora indicator rule", 3,
                "Three-player game. Dora indicator is 1m, and this ruleset uses 1m -> 9m. What is true?",
                "1m 9m 2p 3p 4p 5p 6p 7p 3s 4s 5s E E 9p",
                "Kita declared: 1", "Dora indicator: 1m -> Dora: 9m",
                new String[]{"9m is dora and Kita adds bonus value", "1m is always the only dora", "Kita removes dora", "Dora indicators do not work in sanma"}, 0,
                "Under this sanma rule, 1m indicates 9m as dora. Kita is treated as bonus value in many three-player rulesets.",
                "Sanma / Dora / Kita"));

        list.add(new Question(
                "y35", MODE_YAKU, "Open Full Flush value", 3,
                "Open hand. You have only pinzu tiles. Which statement is correct?",
                "1p 2p 3p 4p 5p 6p 7p 8p 9p 2p 3p 4p 6p 6p",
                "Open call: 1p2p3p. Your discards: E S W", "Dora: 6p",
                new String[]{"Open Full Flush is valid but reduced", "Full Flush requires honors", "Full Flush is impossible open", "It becomes Half Flush"}, 0,
                "Full Flush can be open, but it is worth less when open than when closed.",
                "Full Flush"));

        list.add(new Question(
                "y36", MODE_YAKU, "Three Quads", 3,
                "You have declared or completed three quads. Which yaku is associated with this rare structure?",
                "2m 2m 2m 2m 5p 5p 5p 5p 8s 8s 8s 8s R R",
                "Calls: kan 2m, kan 5p, kan 8s", "Dora: R",
                new String[]{"Three Quads", "All Simples", "Pinfu", "Pure Double Sequence"}, 0,
                "Three Quads is a rare yaku based on completing three kan sets. It is different from All Triplets, although the hand may also have triplet-like structure.",
                "Three Quads"));
    }

    private static void addReadingQuestions(ArrayList<Question> list) {
        list.add(new Question(
                "r01", MODE_READING, "Basic Riichi defense", 1,
                "Opponent declared Riichi. You are far from tenpai. What is the best beginner defensive idea?",
                "1m 3m 5m 8m 2p 4p 7p 9p 1s 3s 6s 8s Wh G",
                "Riichi player's discards: E 9m Wh 2s 5p", "Dora: 7p",
                new String[]{"Discard genbutsu first", "Push dora immediately", "Discard random middle tiles", "Call chi to move faster"}, 0,
                "Against Riichi, tiles already discarded by that opponent are genbutsu and are the safest first choice. If you are far from tenpai, folding is usually correct.",
                "Defense / genbutsu"));

        list.add(new Question(
                "r02", MODE_READING, "Safe tile against Riichi", 1,
                "Opponent declared Riichi. Which tile is safest against that player?",
                "2m 4m 6m 8m 2p 5p 7p 1s 4s 6s Wh G R E",
                "Riichi player's discards: 1m 9m E 3p 7s, then Riichi", "Dora: 5p",
                new String[]{"E", "5p", "R", "6m"}, 0,
                "East is already in the Riichi player's river, so it is genbutsu. The other tiles may still be dangerous.",
                "Defense / genbutsu"));

        list.add(new Question(
                "r03", MODE_READING, "Ippatsu turn", 1,
                "Opponent declared Riichi on the previous discard. You are not tenpai. What should you prioritize?",
                "2m 4m 6m 8m 2p 5p 7p 1s 4s 6s Wh G R 3p",
                "Riichi player's discards: 9m 1p 3p, then Riichi", "Dora: 5p",
                new String[]{"Avoid risk and discard genbutsu", "Discard dora because it is valuable", "Always push during Ippatsu", "Ignore the Riichi declaration"}, 0,
                "During the Ippatsu turn, dealing in can give the opponent extra value. If you are not ready, use the safest available tile.",
                "Ippatsu / defense"));

        list.add(new Question(
                "r04", MODE_READING, "Open dragon call", 1,
                "Opponent called pon on red dragons early. What does this tell you?",
                "2m 3m 5m 6m 8m 3p 4p 7p 8p 2s 4s 6s R",
                "Opponent call: R R R. Their discards: 1m 9p N", "Dora: 4p",
                new String[]{"They already have a yaku", "They cannot win because the hand is open", "They must declare Riichi", "They are certainly going for Pinfu"}, 0,
                "A dragon triplet gives Value Honor, so the opponent already has a valid yaku and can win with an open hand.",
                "Reading / Value Honor"));

        list.add(new Question(
                "r05", MODE_READING, "Possible Half Flush", 1,
                "Opponent has called two pinzu sequences and has discarded mostly manzu and souzu. What should you suspect?",
                "1m 3m 5m 7m 9m 2p 4p 6p 8p 1s 4s 7s N",
                "Opponent calls: 2p3p4p, 6p7p8p. Their discards: 1m 9m 2s 8s", "Dora: 5p",
                new String[]{"Half Flush or Full Flush in pinzu", "They must have Pinfu", "They cannot have a yaku", "They are certainly going for Seven Pairs"}, 0,
                "Calling several groups in one suit while discarding other suits often suggests Half Flush or Full Flush pressure.",
                "Reading / suit concentration"));

        list.add(new Question(
                "r06", MODE_READING, "How far from tenpai", 1,
                "Look at your 13-tile hand. How far is it from winning?",
                "2m 3m 4m 4p 5p 6p R R R E E 6s 7s",
                "Your discards: 1m 9p", "Dora: 5s",
                new String[]{"Tenpai", "1-shanten", "2-shanten", "Already complete"}, 0,
                "The hand has 234m, 456p, RRR, EE and 6s7s. It is waiting on 5s or 8s, so it is tenpai.",
                "Tenpai recognition"));

        list.add(new Question(
                "r07", MODE_READING, "One shanten recognition", 1,
                "Look at your 13-tile hand. What is the most accurate description?",
                "2m 3m 4m 4p 5p 6p 3s 4s 6s 7s E E 9m",
                "Your discards: N Wh", "Dora: 5s",
                new String[]{"1-shanten", "Tenpai", "Already complete", "Impossible to improve"}, 0,
                "The hand has two completed sequences, a pair, and two incomplete sequence shapes. It needs one improvement to reach tenpai.",
                "Shanten recognition"));

        list.add(new Question(
                "r08", MODE_READING, "Dora and push/fold", 1,
                "Opponent declared Riichi. You are far from tenpai and have a safe tile. What should you avoid?",
                "2m 4m 6m 8m 2p 5p 7p 1s 4s 6s E G R 9p",
                "Riichi player's discards: 9m E 3p 7s, then Riichi", "Dora: 5p",
                new String[]{"Discarding live dora while folding", "Using genbutsu", "Folding when far from tenpai", "Avoiding the Ippatsu risk"}, 0,
                "If you are folding, pushing live dora is usually a large unnecessary risk. Safe tiles should be used first.",
                "Defense / dora danger"));

        list.add(new Question(
                "r09", MODE_READING, "Safe against one player only", 1,
                "A tile is genbutsu against the Riichi player. What is the important limitation?",
                "2m 3m 6m 7m 9m 2p 4p 6p 8p 3s 5s 7s E Wh",
                "Riichi player's discards: E 9m Wh 4s. Dealer has two open calls.", "Dora: 5p",
                new String[]{"It may still be dangerous to another opponent", "It is always safe against everyone", "It becomes dora", "It cannot be discarded"}, 0,
                "Genbutsu is player-specific. A tile safe against the Riichi player can still deal into another player's open hand.",
                "Defense / table awareness"));

        list.add(new Question(
                "r10", MODE_READING, "Open All Simples read", 1,
                "Opponent has called two sequences and has discarded terminals and honors early. What is a common read?",
                "1m 2m 7m 9m 1p 3p 8p 9p 2s 4s 5s E S W",
                "Opponent calls: 3p4p5p, 4s5s6s. Their discards: E 1m 9m 1p", "Dora: 6s",
                new String[]{"They may be going for All Simples", "They must have All Honors", "They cannot win open", "They must be in Full Flush"}, 0,
                "Early terminal and honor discards plus open simple sequences often suggest an All Simples hand.",
                "Reading / All Simples"));

        list.add(new Question(
                "r11", MODE_READING, "Kita value awareness", 1,
                "Three-player game. Opponent has declared three Kita and then declares Riichi. What changes?",
                "1m 9m 2p 4p 5p 7p 8p 3s 5s 6s 8s Wh G R",
                "Opponent: Riichi. Kita declared: 3", "Dora: 5p",
                new String[]{"Their hand is likely more valuable, so defense becomes more important", "Kita makes Riichi invalid", "Kita means they cannot have Dora", "Kita makes all tiles safe"}, 0,
                "Kita adds bonus value in many three-player rulesets. A Riichi hand with several Kita can be expensive.",
                "Sanma / Kita / defense"));

        list.add(new Question(
                "r12", MODE_READING, "Do not call when folding", 1,
                "Opponent declared Riichi. You are far from tenpai and have safe tiles. Should you call chi to improve speed?",
                "2m 4m 6m 8m 2p 5p 7p 1s 4s 6s E G R 9p",
                "Riichi player's discards: E 9m 3p 7s, then Riichi", "Dora: 5p",
                new String[]{"No, calling usually reduces defensive options", "Yes, always call after Riichi", "Yes, because calls create Ura Dora", "No, because calls are illegal after Riichi"}, 0,
                "When folding, calling often removes safe tiles from your hand and forces you to discard something dangerous.",
                "Defense / calling discipline"));

        list.add(new Question(
                "r13", MODE_READING, "Suji basics", 2,
                "Opponent declared Riichi and has discarded 6p. Which tile is relatively safer against a two-sided wait, but not guaranteed safe?",
                "1m 4m 7m 2p 3p 5p 8p 2s 5s 8s E S W 9s",
                "Riichi player's discards: 1m 6p 9s, then Riichi", "Dora: 5p",
                new String[]{"3p", "5p", "E", "7m"}, 0,
                "Because 6p is in the Riichi player's discard, 3p is suji against a 3p-6p two-sided wait. It can still be dangerous to closed waits or pair waits.",
                "Defense / suji"));

        list.add(new Question(
                "r14", MODE_READING, "Suji is not absolute safety", 2,
                "You have no genbutsu. One tile is suji, but it is also dora. What should you remember?",
                "1m 4m 7m 2p 3p 5p 8p 2s 5s 8s E S W G",
                "Riichi player's discards: 6p 9s 1m, then Riichi", "Dora: 3p",
                new String[]{"Suji is only a hint, and dora suji can still be dangerous", "Suji dora is always safe", "Dora cannot deal in", "Suji works against every wait type"}, 0,
                "Suji only reduces the chance of a two-sided wait. It does not protect against closed waits, pair waits, or value-heavy danger.",
                "Defense / suji danger"));

        list.add(new Question(
                "r15", MODE_READING, "Kabe idea", 2,
                "All four 7m are visible on the table. What does that suggest about 8m?",
                "2m 4m 6m 8m 2p 5p 7p 1s 4s 6s Wh G R E",
                "Visible tiles: 7m 7m 7m 7m. Riichi player discards: 1p 9p 3s, then Riichi", "Dora: 5p",
                new String[]{"8m is safer against two-sided waits, but not completely safe", "8m is guaranteed safe", "8m becomes dora", "8m cannot be discarded"}, 0,
                "If all 7m are visible, some two-sided waits involving 8m become impossible. However, 8m can still deal into closed waits, pair waits or other shapes.",
                "Defense / kabe"));

        list.add(new Question(
                "r16", MODE_READING, "Late dangerous discard", 2,
                "Opponent discarded 5p late and then declared Riichi. What is a reasonable caution?",
                "1m 3m 5m 7m 2p 4p 6p 8p 1s 3s 5s 7s E G",
                "Opponent river: 9m 1s E 5p, then Riichi", "Dora: 4p",
                new String[]{"Tiles around that late discard can still be dangerous", "All pinzu are now completely safe", "Only honors can deal in", "The opponent cannot have pinzu waits"}, 0,
                "A late middle discard can come from shape fixing. It does not make the nearby suit automatically safe.",
                "Reading / late discard"));

        list.add(new Question(
                "r17", MODE_READING, "Flush pressure and your blockers", 2,
                "Opponent is clearly aiming at pinzu Half Flush. You hold many pinzu tiles. What is the best interpretation?",
                "2p 3p 4p 5p 6p 7p 8p 8p 1m 9m 2s 6s E Wh",
                "Opponent calls: 6p7p8p, pon E. Their discards: 1m 9m 2s 8s", "Dora: 5p",
                new String[]{"Your pinzu reduce some of their outs, but live pinzu can still be dangerous", "All pinzu are safe because you hold many", "They cannot complete Half Flush anymore", "You should discard dora 5p first"}, 0,
                "Holding many tiles from their target suit blocks some shapes, but it does not make all remaining suit tiles safe. Live dora or middle pinzu can still deal in.",
                "Reading / Half Flush"));

        list.add(new Question(
                "r18", MODE_READING, "Competing for same suit", 2,
                "You and an opponent both seem to need souzu for a flush. What practical problem appears?",
                "2s 3s 4s 4s 5s 6s 7s 8s 9s E E R R 5m",
                "Opponent calls: 3s4s5s, 6s7s8s. Their discards: 1m 9m 2p 8p", "Dora: 8s",
                new String[]{"The suit is contested, so both your progress and safety get worse", "Both players are guaranteed to complete the flush", "Souzu are now completely safe", "Your Half Flush automatically becomes yakuman"}, 0,
                "If both players need the same suit, key tiles may be blocked. At the same time, discarding that suit can become dangerous.",
                "Strategy / contested suit"));

        list.add(new Question(
                "r19", MODE_READING, "Wait tile count", 2,
                "You are tenpai on 5s or 8s. Visible tiles: two 5s are visible and one 8s is visible. How many winning tiles remain?",
                "2m 3m 4m 4p 5p 6p R R R E E 6s 7s",
                "Visible tiles: 5s 5s 8s", "Dora: 5s",
                new String[]{"5 tiles remain", "8 tiles remain", "3 tiles remain", "0 tiles remain"}, 0,
                "There are four 5s and four 8s in total. Two 5s and one 8s are visible, so 2 remaining 5s plus 3 remaining 8s equals 5 outs.",
                "Tenpai / outs count"));

        list.add(new Question(
                "r20", MODE_READING, "Bad wait under pressure", 2,
                "Opponent declared Riichi. You are tenpai, but only on a single live non-dora honor tile. Your hand is cheap. What is usually best?",
                "2m 3m 4m 4p 5p 6p 6s 7s 8s 3m 4m 5m G",
                "Your wait: G only. Riichi player's discards: 1m 9m 2p, then Riichi", "Dora: 9s",
                new String[]{"Consider folding if you have safe tiles", "Always push any tenpai", "Declare Kita", "Discard dora first"}, 0,
                "Tenpai alone is not always enough. A cheap hand with a poor single wait often does not justify pushing dangerous tiles against Riichi.",
                "Push/fold"));

        list.add(new Question(
                "r21", MODE_READING, "Valuable tenpai push", 2,
                "Opponent declared Riichi. You are already tenpai with a strong hand and a good two-sided wait. What is more reasonable?",
                "2m 3m 4m 3p 4p 5p* 6p 7p 8p 3s 4s 5s 7s 7s",
                "Your hand: Riichi available. Opponent Riichi. Your wait: 6s/9s", "Dora: 7p",
                new String[]{"Pushing can be reasonable because the hand is valuable and ready", "Always fold any time someone declares Riichi", "Discard your pair immediately", "Call chi after declaring Riichi"}, 0,
                "A valuable tenpai with a good wait can justify pushing, especially compared to a cheap far-away hand.",
                "Push/fold / value"));

        list.add(new Question(
                "r22", MODE_READING, "Dealer pressure", 2,
                "Dealer declared Riichi. You are 2-shanten with a cheap hand. What is the practical decision?",
                "1m 3m 5m 8m 2p 4p 7p 9p 1s 3s 6s 8s Wh G",
                "Dealer Riichi discards: 1m 9m Wh 2s 5p", "Dora: 7p",
                new String[]{"Fold aggressively", "Push because dealer Riichi is weaker", "Discard dora first", "Call any tile to chase"}, 0,
                "Dealer Riichi is dangerous because dealer wins are more expensive. A cheap 2-shanten hand usually folds.",
                "Dealer Riichi / defense"));

        list.add(new Question(
                "r23", MODE_READING, "Open All Triplets read", 2,
                "Opponent has poned two pairs and is discarding sequence tiles. What should you suspect?",
                "2m 3m 5m 6m 8m 3p 4p 7p 8p 2s 4s 6s N",
                "Opponent calls: pon 8p, pon R. Their discards: 3m 4m 5s 6s", "Dora: R",
                new String[]{"All Triplets or Value Honor pressure", "Pinfu", "Pure Double Sequence", "They cannot win open"}, 0,
                "Multiple pon calls often suggest All Triplets or a value-heavy open hand. Sequence discards support that reading.",
                "Reading / All Triplets"));

        list.add(new Question(
                "r24", MODE_READING, "All Green warning", 2,
                "Opponent has called green dragon and only green souzu tiles. What rare hand should be considered?",
                "2s 3s 4s 6s 8s G 1m 9m 2p 7p E S Wh R",
                "Opponent calls: GGG, 2s3s4s. Their discards: 1m 9m 5s 7p", "Dora: G",
                new String[]{"All Green or souzu Half Flush", "All Simples only", "Seven Pairs only", "Full Outside Hand in manzu"}, 0,
                "A hand built from green dragons and green souzu tiles may point to All Green, although Half Flush is more common.",
                "Reading / rare hand"));

        list.add(new Question(
                "r25", MODE_READING, "Suji trap", 3,
                "Opponent discarded 5p and declared Riichi later. You have no genbutsu. Which statement is best?",
                "1m 4m 7m 2p 3p 5p 8p 2s 5s 8s E S W G",
                "Riichi player's river: 9m 1s 5p 7m 3s, then Riichi", "Dora: 2p",
                new String[]{"2p may be suji, but it is still dangerous because suji is not full safety", "2p is guaranteed safe because 5p is discarded", "Dora cannot deal in", "All pinzu are safe after 5p"}, 0,
                "A discarded 5p makes 2p and 8p look safer against two-sided waits, but they can still lose to closed waits, pair waits or traps. Dora suji is especially suspicious.",
                "Defense / suji trap"));

        list.add(new Question(
                "r26", MODE_READING, "Genbutsu over suji", 3,
                "Opponent declared Riichi. You have one genbutsu and one suji tile. Which should usually come first?",
                "1m 4m 7m 2p 3p 5p 8p 2s 5s 8s E S W 9s",
                "Riichi player's discards: 6p 9s 1m, then Riichi", "Dora: 5p",
                new String[]{"9s", "3p", "5p", "E"}, 0,
                "The 9s is genbutsu. Genbutsu is safer than suji because suji only protects against some two-sided waits.",
                "Defense / genbutsu over suji"));

        list.add(new Question(
                "r27", MODE_READING, "Furiten wait", 3,
                "You are tenpai on 2s/5s, but 5s is in your own discard. What is true?",
                "2m 3m 4m 4p 5p 6p 6s 7s 8s 3s 4s E E",
                "Your own discards include: 5s", "Dora: 4p",
                new String[]{"You are furiten and cannot win by Ron on that wait", "You can freely Ron on 5s", "The hand becomes All Simples automatically", "Furiten only applies to open hands"}, 0,
                "If one of your winning tiles is in your own discard, the wait is furiten. You cannot win by Ron, though you may still win by self-draw.",
                "Furiten / tenpai"));

        list.add(new Question(
                "r28", MODE_READING, "Effective wait quality", 3,
                "You are tenpai on 3p/6p, but all four 3p are visible. What is your real wait?",
                "2m 3m 4m 4s 5s 6s R R R E E 4p 5p",
                "Visible tiles: 3p 3p 3p 3p", "Dora: 6p",
                new String[]{"Only 6p remains as a real winning tile", "The wait is still equally 3p/6p", "You have no yaku", "You are not tenpai"}, 0,
                "The shape is technically 3p/6p, but if all 3p are visible, only 6p can still complete the hand.",
                "Wait quality / visible tiles"));

        list.add(new Question(
                "r29", MODE_READING, "Pushing live dora into open hand", 3,
                "Dealer has an open Half Flush in pinzu. You are 2-shanten. Which discard is most dangerous?",
                "2m 4m 6m 8m 2p 5p 7p 1s 4s 6s Wh G R E",
                "Dealer calls: 3p4p5p, 6p7p8p. Dealer discards: 1m 9m 2s 8s", "Dora: 5p",
                new String[]{"5p", "E", "1s", "8m"}, 0,
                "The dealer is likely building pinzu Half Flush or Full Flush, and 5p is also dora. Discarding it while far from tenpai is very dangerous.",
                "Reading / dora danger"));

        list.add(new Question(
                "r30", MODE_READING, "Two threats at once", 3,
                "One opponent declared Riichi. Dealer also has two open calls in pinzu. Which tile choice is most defensively aware?",
                "2m 4m 6m 8m 2p 5p 7p 1s 4s 6s Wh G R E",
                "Riichi player's discards: E 9m Wh 2s. Dealer calls: 3p4p5p, 6p7p8p", "Dora: 5p",
                new String[]{"Wh, because it is genbutsu to Riichi and less connected to dealer's pinzu hand", "5p, because dora is always safe", "7p, because dealer needs pinzu", "G, because honors cannot deal in"}, 0,
                "With multiple threats, you need a tile that is safe or relatively safe against as many players as possible. Wh is genbutsu to the Riichi player and not part of dealer's visible suit pressure.",
                "Defense / multiple opponents"));

        list.add(new Question(
                "r31", MODE_READING, "Silent dealer danger", 3,
                "Dealer has two open calls and starts discarding strong middle tiles. No Riichi is needed. What should you consider?",
                "1m 3m 5m 7m 2p 4p 6p 8p 1s 3s 5s 7s E G",
                "Dealer calls: RRR, 4p5p6p. Dealer discards: 2m 5s 6s", "Dora: 4p",
                new String[]{"Dealer may already be tenpai with a valid open hand", "Dealer cannot win without Riichi", "Open hands are always cheap", "Dragon pon removes the yaku"}, 0,
                "Dealer already has Value Honor from the dragon triplet. Strong discards after calls may indicate tenpai or near-tenpai.",
                "Reading / open dealer hand"));

        list.add(new Question(
                "r32", MODE_READING, "Choosing not to chase Riichi", 3,
                "You are 1-shanten, but your hand is cheap and your best discard is live dora. Opponent declared Riichi. What is usually best?",
                "2m 3m 4m 6m 7m 8m 3p 4p 5p 6s 7s E E 5s",
                "Riichi player's discards: 1m 9m Wh 2s, then Riichi", "Dora: 5s",
                new String[]{"Fold or take a safer route", "Always discard 5s to chase", "Declare Riichi immediately", "Call pon on East from your own hand"}, 0,
                "A cheap 1-shanten hand often does not justify discarding live dora into Riichi. Value, distance and safety all matter.",
                "Push/fold / dora"));

        list.add(new Question(
                "r33", MODE_READING, "Strong chase condition", 3,
                "Opponent declared Riichi. You are tenpai with Riichi, All Simples, Pinfu, Red Dora and normal Dora. What is reasonable?",
                "2m 3m 4m 3p 4p 5p* 6p 7p 8p 3s 4s 5s 7s 7s",
                "Your wait: 6s/9s. Opponent Riichi.", "Dora: 7p",
                new String[]{"Chasing can be reasonable because the hand is valuable with a good wait", "Always fold even with valuable tenpai", "Break the hand immediately", "Discard the pair because pairs are unsafe"}, 0,
                "A ready hand with good wait quality and high value can justify pushing against Riichi.",
                "Push/fold / strong tenpai"));

        list.add(new Question(
                "r34", MODE_READING, "Sanma 9m dora danger", 3,
                "Three-player game. Dora indicator is 1m, so 9m is dora in this ruleset. Opponent declared Riichi. What should you avoid?",
                "1m 9m 2p 4p 5p 7p 8p 3s 5s 6s 8s Wh G R",
                "Riichi player's discards: 1p 9p Wh 3s, then Riichi", "Dora indicator: 1m -> Dora: 9m",
                new String[]{"Discarding live 9m if you have safer tiles", "Using Wh if it is genbutsu", "Counting Kita value", "Checking visible tiles"}, 0,
                "In this sanma rule, 9m is dora. Pushing a live dora into Riichi is dangerous, especially if safe tiles are available.",
                "Sanma / dora defense"));

        list.add(new Question(
                "r35", MODE_READING, "Reading Full Flush commitment", 3,
                "Opponent has discarded every suit except souzu and has two souzu calls. What is the danger?",
                "1m 2m 9m 2p 5p 8p 2s 4s 5s 6s 8s E Wh R",
                "Opponent calls: 2s3s4s, 6s7s8s. Their discards: 1m 9m 2p 8p E Wh", "Dora: 6s",
                new String[]{"Live souzu, especially middle souzu and dora, may be very dangerous", "Souzu are safe because they have called souzu", "They cannot have Full Flush open", "Only honors can deal in"}, 0,
                "When an opponent commits to one suit, live tiles in that suit become dangerous, especially connected middle tiles and dora.",
                "Reading / Full Flush"));

        list.add(new Question(
                "r36", MODE_READING, "Do not overread rare hands", 3,
                "Opponent has green dragon pon and some souzu calls. You suspect All Green, but what is the practical reading?",
                "2s 3s 4s 6s 8s G 1m 9m 2p 7p E S Wh R",
                "Opponent calls: GGG, 2s3s4s. Their discards: 1m 9m 5s 7p", "Dora: G",
                new String[]{"Respect the rare possibility, but also read the common souzu Half Flush", "Assume All Green is guaranteed", "Ignore the hand because rare hands never happen", "Discard green dragon because it is safe"}, 0,
                "Rare hands matter, but practical reading should also consider common patterns. Green dragon plus souzu calls often means at least Half Flush pressure.",
                "Reading / rare hand awareness"));
    }
}
