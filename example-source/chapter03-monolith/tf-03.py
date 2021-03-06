
import sys, string
# [단어, 빈도] 쌍으로 구성된 전역 리스트
word_freqs = []
# 의미 없는 단어 목록
with open('../stop_words.txt') as f:
    stop_words = f.read().split(',')
stop_words.extend(list(string.ascii_lowercase))

# 파일 전체를 한 번에 한 줄씩 순회한다
for line in open(sys.argv[1]):
    start_char = None
    i = 0
    for c in line:
        if start_char == None:
            if c.isalnum():
                # 단어 시작을 찾았다
                start_char = i
        else:
            if not c.isalnum():
                # 단어 끝을 찾았으므로 처리한다
                found = False
                word = line[start_char:i].lower()
                # 의미 없는 단어를 무시한다
                if word not in stop_words:
                    pair_index = 0
                    # 이미 존재하는지 확인하자
                    for pair in word_freqs:
                        if word == pair[0]:
                            pair[1] += 1
                            found = True
                            found_at = pair_index
                            break
                        pair_index += 1
                    if not found:
                        word_freqs.append([word, 1])
                    elif len(word_freqs) > 1:
                        # 재배열해야 한다
                        for n in reversed(range(pair_index)):
                            if word_freqs[pair_index][1] > word_freqs[n][1]:
                                # 교환한다
                                word_freqs[n], word_freqs[pair_index] = word_freqs[pair_index], word_freqs[n]
                                pair_index = n
                start_char = None
        i += 1

for tf in word_freqs[0:25]:
    print tf[0], ' - ', tf[1]


